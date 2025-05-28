package com.mojian.controller.notifications;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mojian.common.Result;
import com.mojian.entity.SysNotifications;
import com.mojian.service.NotificationsService;
import com.mojian.vo.notifications.NotificationsListVo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: quequnlong
 * @date: 2025/3/21
 * @description:
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationsController {

    private final NotificationsService notificationsService;

    @GetMapping("/unReadNum")
    @ApiOperation(value = "获取未读消息数量")
    public Result<Map<String,Integer>> getUnReadNum() {
        return Result.success( notificationsService.getUnReadNum());
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询消息通知")
    public Result<IPage<NotificationsListVo>> page(SysNotifications notifications) {
        return Result.success(notificationsService.page(notifications));
    }

    @GetMapping("/read/{id}")
    @ApiOperation(value = "已读消息通知")
    public Result<Void> doRead(@PathVariable Long id) {
        notificationsService.doRead(id);
        return Result.success();
    }

    @GetMapping("/read/all")
    @ApiOperation(value = "全部已读")
    public Result<Void> allRead() {
        notificationsService.allRead();
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除消息")
    public Result<Void> delete(@PathVariable Long id) {
        notificationsService.delete(id);
        return Result.success();
    }

    @GetMapping("/is-unread")
    @ApiOperation(value = "获取的未读消息")
    public Result<Boolean> getMyIsUnread() {
        return Result.success(notificationsService.getMyIsUnread());
    }
}
