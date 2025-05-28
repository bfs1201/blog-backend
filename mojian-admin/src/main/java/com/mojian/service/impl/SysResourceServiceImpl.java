package com.mojian.service.impl;

import java.util.List;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import com.mojian.mapper.SysResourceMapper;
import com.mojian.entity.SysResource;
import com.mojian.service.SysResourceService;
import com.mojian.utils.PageUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;

/**
 * 资源表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {

    /**
     * 查询资源表分页列表
     */
    @Override
    public IPage<SysResource> selectPage(SysResource sysResource) {
        LambdaQueryWrapper<SysResource> wrapper = new LambdaQueryWrapper<>();
        // 构建查询条件
        wrapper.eq(sysResource.getId() != null, SysResource::getId, sysResource.getId());
        wrapper.eq(sysResource.getUserId() != null, SysResource::getUserId, sysResource.getUserId());
        wrapper.eq(sysResource.getName() != null, SysResource::getName, sysResource.getName());
        wrapper.eq(sysResource.getCategory() != null, SysResource::getCategory, sysResource.getCategory());
        wrapper.eq(sysResource.getDownloads() != null, SysResource::getDownloads, sysResource.getDownloads());
        wrapper.eq(sysResource.getIsFree() != null, SysResource::getIsFree, sysResource.getIsFree());
        wrapper.eq(sysResource.getPayType() != null, SysResource::getPayType, sysResource.getPayType());
        wrapper.eq(sysResource.getPanPath() != null, SysResource::getPanPath, sysResource.getPanPath());
        wrapper.eq(sysResource.getPanCode() != null, SysResource::getPanCode, sysResource.getPanCode());
        wrapper.eq(sysResource.getCreateTime() != null, SysResource::getCreateTime, sysResource.getCreateTime());
        return page(PageUtil.getPage(), wrapper);
    }


    /**
     * 新增资源表
     */
    @Override
    public boolean insert(SysResource sysResource) {
        sysResource.setUserId(StpUtil.getLoginIdAsLong());
        return save(sysResource);
    }

    /**
     * 修改资源表
     */
    @Override
    public boolean update(SysResource sysResource) {
        return updateById(sysResource);
    }

    /**
     * 批量删除资源表
     */
    @Override
    public boolean deleteByIds(List<Long> ids) {
        return removeByIds(ids);
    }
}
