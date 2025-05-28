package com.mojian.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mojian.entity.SysNotifications;
import com.mojian.exception.ServiceException;
import com.mojian.mapper.SysNotificationsMapper;
import com.mojian.service.NotificationsService;
import com.mojian.utils.PageUtil;
import com.mojian.vo.notifications.NotificationsListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: quequnlong
 * @date: 2025/3/21
 * @description:
 */
@Service
@RequiredArgsConstructor
public class NotificationsServiceImpl implements NotificationsService {

    private final SysNotificationsMapper baseMapper;


    public IPage<NotificationsListVo> page(SysNotifications notifications) {
        notifications.setUserId(StpUtil.getLoginIdAsLong());
        return baseMapper.selectNotificationsPage(PageUtil.getPage(),notifications);
    }

    @Override
    public void doRead(Long id) {
        SysNotifications notifications = baseMapper.selectById(id);
        if (notifications == null) {
            throw new ServiceException("消息通知不存在");
        }
        notifications.setIsRead(1);
        baseMapper.updateById(notifications);
    }

    @Override
    public void allRead() {
        baseMapper.update(SysNotifications.builder().isRead(1).build(),new LambdaQueryWrapper<SysNotifications>()
                .eq(SysNotifications::getUserId, StpUtil.getLoginIdAsLong()));
    }

    @Override
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public Map<String, Integer> getUnReadNum() {
        return baseMapper.getUnReadNum(StpUtil.getLoginIdAsLong());
    }

    @Override
    public Boolean getMyIsUnread() {
        List<SysNotifications> sysNotifications = baseMapper.selectList(new LambdaQueryWrapper<SysNotifications>()
                .eq(SysNotifications::getUserId, StpUtil.getLoginIdAsLong())
                .eq(SysNotifications::getIsRead, 0));
        return CollectionUtil.isNotEmpty(sysNotifications);
    }

}
