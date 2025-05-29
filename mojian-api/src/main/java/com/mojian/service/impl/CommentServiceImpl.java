package com.mojian.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mojian.entity.SysNotifications;
import com.mojian.service.CommentService;
import com.mojian.utils.NotificationsUtil;
import com.mojian.utils.SensitiveUtil;
import com.mojian.vo.comment.CommentListVo;
import com.mojian.entity.SysComment;
import com.mojian.mapper.SysCommentMapper;
import com.mojian.utils.IpUtil;
import com.mojian.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Resource
    private IpUtil ipUtil;

    private final SysCommentMapper sysCommentMapper;

    private final NotificationsUtil notificationsUtil;

    @Override
    public IPage<CommentListVo> getComments(Integer articleId,String sortType) {
        IPage<CommentListVo> page = sysCommentMapper.getComments(PageUtil.getPage(),articleId,sortType);
        //获取所有子评论
        page.getRecords().forEach(commentListVo -> {
            List<CommentListVo> children = sysCommentMapper.getChildrenComment(commentListVo.getId());
            commentListVo.setChildren(children);
        });
        return page;
    }

    @Override
    public void add(SysComment sysComment) {

        String ip = ipUtil.getIp();
        sysComment.setIp(ip);
        sysComment.setIpSource(ipUtil.getCityInfo(ip));
        sysComment.setUserId(StpUtil.getLoginIdAsLong());
        sysComment.setContent(SensitiveUtil.filter(sysComment.getContent()));

        sysCommentMapper.insert(sysComment);

        ThreadUtil.execAsync(() -> {
            //发送通知事件
            SysNotifications notifications = SysNotifications.builder()
                    .title(sysComment.getReplyUserId() != null ? "评论回复通知" : "新评论通知")
                    .message(sysComment.getContent())
                    .articleId(sysComment.getArticleId())
                    .isRead(0)
                    .type("comment")
                    .fromUserId(sysComment.getReplyUserId())
                    .build();
            notificationsUtil.publish(notifications);
        });
    }
}
