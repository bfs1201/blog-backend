package com.mojian.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mojian.entity.SysNotifications;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mojian.vo.notifications.NotificationsListVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 消息通知表 Mapper接口
 */
@Mapper
public interface SysNotificationsMapper extends BaseMapper<SysNotifications> {
    /**
     * 分页查询消息通知
     *
     * @param page
     * @return
     */
    IPage<NotificationsListVo> selectNotificationsPage(@Param("page") Page<Object> page, @Param("notifications") SysNotifications notifications);

    @MapKey("type")
    Map<String, Integer> getUnReadNum(long userId);

}
