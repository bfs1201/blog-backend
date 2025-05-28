package com.mojian.controller.system;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.mojian.entity.SysConfig;
import com.mojian.service.SysConfigService;
import com.mojian.common.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 参数配置表 控制器
 */
@RestController
@RequestMapping("/sys/config")
@RequiredArgsConstructor
@Api(tags = "参数配置表管理")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    @GetMapping("/list")
    @ApiOperation(value = "获取参数配置表列表")
    public Result<IPage<SysConfig>> list(SysConfig sysConfig) {
        return Result.success(sysConfigService.selectPage(sysConfig));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取参数配置表详情")
    public Result<SysConfig> getInfo(@PathVariable("id") Long id) {
        return Result.success(sysConfigService.getById(id));
    }

    @PostMapping("/add")
    @SaCheckPermission("sys:config:add")
    @ApiOperation(value = "添加参数配置表")
    public Result<Object> add(@RequestBody SysConfig sysConfig) {
        return Result.success(sysConfigService.insert(sysConfig));
    }

    @PutMapping("/update")
    @SaCheckPermission("sys:config:update")
    @ApiOperation(value = "修改参数配置表")
    public Result<Object> edit(@RequestBody SysConfig sysConfig) {
        return Result.success(sysConfigService.update(sysConfig));
    }

    @DeleteMapping("/delete/{ids}")
    @SaCheckPermission("sys:config:delete")
    @ApiOperation(value = "删除参数配置表")
    public Result<Object> remove(@PathVariable List<Long> ids) {
        return Result.success(sysConfigService.deleteByIds(ids));
    }

    @SaIgnore
    @GetMapping("/getConfigByKey/{key}")
    @ApiOperation(value = "根据key获取参数配置详情")
    public Result<SysConfig> selectConfigByKey(@PathVariable("key") String key) {
        return Result.success(sysConfigService.selectConfigByKey(key));
    }
}
