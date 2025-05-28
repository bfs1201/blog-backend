package com.mojian.utils;

import com.mojian.entity.SysArticle;
import com.mojian.entity.SysNotifications;
import com.mojian.mapper.SysArticleMapper;
import com.mojian.mapper.SysNotificationsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: quequnlong
 * @date: 2025/3/24
 * @description:
 */
@Component
@RequiredArgsConstructor
public class NotificationsUtil {

    private final SysArticleMapper sysArticleMapper;

    private final SysNotificationsMapper baseMapper;


    public void publish(SysNotifications sysNotifications) {
        SysArticle sysArticle = new SysArticle();
        if (sysNotifications.getArticleId() != null) {
            sysArticle = sysArticleMapper.selectById(sysNotifications.getArticleId());
            sysNotifications.setUserId(sysArticle.getUserId());
        }
        switch (sysNotifications.getType()) {
            case "comment":
                break;
            case "like":
                sysNotifications.setMessage("点赞了文章：" + sysArticle.getTitle());
                break;
            case "unLike":
                break;
            default:
                break;
        }
        baseMapper.insert(sysNotifications);
    }
}
