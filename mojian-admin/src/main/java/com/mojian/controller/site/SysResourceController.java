package com.mojian.controller.site;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.mojian.entity.SysResource;
import com.mojian.service.SysResourceService;
import com.mojian.common.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 资源表 控制器
 */
@RestController
@RequestMapping("/api/sys/resource")
@RequiredArgsConstructor
@Api(tags = "资源表管理")
public class SysResourceController {

    private final SysResourceService sysResourceService;

    @GetMapping("/list")
    @ApiOperation(value = "获取资源表列表")
    public Result<IPage<SysResource>> list(SysResource sysResource) {
        return Result.success(sysResourceService.selectPage(sysResource));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取资源表详情")
    public Result<SysResource> getInfo(@PathVariable("id") Long id) {
        return Result.success(sysResourceService.getById(id));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:resource:add")
    @ApiOperation(value = "添加资源表")
    public Result<Object> add(@RequestBody SysResource sysResource) {
        return Result.success(sysResourceService.insert(sysResource));
    }

    @PutMapping("/update")
    @SaCheckPermission("sys:resource:update")
    @ApiOperation(value = "修改资源表")
    public Result<Object> edit(@RequestBody SysResource sysResource) {
        return Result.success(sysResourceService.update(sysResource));
    }

    @DeleteMapping("/delete/{ids}")
    @SaCheckPermission("sys:resource:delete")
    @ApiOperation(value = "删除资源表")
    public Result<Object> remove(@PathVariable List<Long> ids) {
        return Result.success(sysResourceService.deleteByIds(ids));
    }
}
