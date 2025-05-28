package com.mojian.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mojian.entity.SysNotifications;
import com.mojian.vo.notifications.NotificationsListVo;

import java.util.Map;

/**
 * @author: quequnlong
 * @date: 2025/3/21
 * @description:
 */
public interface NotificationsService {

    /**
     * 分页查询
     * @param notifications
     * @return
     */
    IPage<NotificationsListVo> page(SysNotifications notifications);

    /**
     * 已读
     * @param id
     */
    void doRead(Long id);

    /**
     * 全部已读
     */
    void allRead();

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 未读消息数量
     * @return
     */
    Map<String, Integer> getUnReadNum();

    /**
     * 是否有未读消息
     * @return
     */
    Boolean getMyIsUnread();


}
