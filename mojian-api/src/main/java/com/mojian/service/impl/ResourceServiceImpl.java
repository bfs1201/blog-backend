package com.mojian.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mojian.common.RedisConstants;
import com.mojian.entity.SysResource;
import com.mojian.enums.ResourceStatusEnum;
import com.mojian.exception.ServiceException;
import com.mojian.mapper.SysResourceMapper;
import com.mojian.service.ResourceService;
import com.mojian.utils.PageUtil;
import com.mojian.utils.RedisUtil;
import com.mojian.vo.resource.SysResourceVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: quequnlong
 * @date: 2025/3/12
 * @description:
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final SysResourceMapper baseMapper;

    private final RedisUtil redisUtil;

    @Override
    public Page<SysResourceVo> getResourceList(SysResource sysResource) {
        return baseMapper.getResourceList(PageUtil.getPage(),sysResource);
    }

    @Override
    public void add(SysResource sysResource) {
        sysResource.setUserId(StpUtil.getLoginIdAsLong());
        sysResource.setStatus(ResourceStatusEnum.AUDIT.getCode());
        baseMapper.insert(sysResource);
    }

    @Override
    public SysResource verify(String code,Long id) {
        String key = RedisConstants.CAPTCHA_CODE_KEY + code;
        if (!redisUtil.hasKey(key)) {
            throw new ServiceException("验证码错误");
        }
        redisUtil.delete(key);

        SysResource sysResource = baseMapper.selectById(id);

        sysResource.setDownloads(sysResource.getDownloads() + 1);
        baseMapper.updateById(sysResource);

        return sysResource;
    }
}
