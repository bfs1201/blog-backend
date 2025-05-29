package com.mojian.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mojian.common.Constants;
import com.mojian.common.Result;
import com.mojian.entity.FileDetail;
import com.mojian.entity.SysFileOss;
import com.mojian.exception.ServiceException;
import com.mojian.service.FileDetailService;
import com.mojian.utils.DateUtil;
import com.mojian.utils.MinIOUtils;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/file")
@Api(tags = "文件管理")
@RequiredArgsConstructor
public class FileController {

    private final FileDetailService fileDetailService;

    private final FileStorageService fileStorageService;

    @Resource
    private MinIOUtils minIOUtils;


    @SaCheckLogin
    @GetMapping("/list")
    @ApiOperation(value = "获取文件记录表列表")
    public Result<IPage<FileDetail>> list(FileDetail fileDetail) {
        return Result.success(fileDetailService.selectPage(fileDetail));
    }

    @SaCheckLogin
    @GetMapping("/getOssConfig")
    @ApiOperation(value = "获取存储平台配置")
    public Result<List<SysFileOss>> getOssConfig() {
        return Result.success(fileDetailService.getOssConfig());
    }

    @SaCheckLogin
    @PostMapping("/addOss")
    @SaCheckPermission("sys:oss:submit")
    @ApiOperation(value = "添加存储平台配置")
    public Result<Void> addOss(@RequestBody SysFileOss sysFileOss) {
        fileDetailService.addOss(sysFileOss);
        if (sysFileOss.getIsEnable() == Constants.YES) {
            fileStorageService.getProperties().setDefaultPlatform(sysFileOss.getPlatform());
        }
        return Result.success();
    }

    @SaCheckLogin
    @PutMapping("/updateOss")
    @SaCheckPermission("sys:oss:submit")
    @ApiOperation(value = "修改存储平台配置")
    public Result<Void> updateOss(@RequestBody SysFileOss sysFileOss) {
        fileDetailService.updateOss(sysFileOss);
        if (sysFileOss.getIsEnable() == Constants.YES) {
            fileStorageService.getProperties().setDefaultPlatform(sysFileOss.getPlatform());
        }
        return Result.success();
    }

    //    @SaCheckLogin
//    @PostMapping("/upload")
//    @ApiOperation(value = "上传文件")
//    public Result<String> upload(MultipartFile file, String source) {
//        String path = DateUtil.parseDateToStr(DateUtil.YYYYMMDD, DateUtil.getNowDate()) + "/";
//        //这个source可在前端上传文件时提供，可用来区分是头像还是文章图片等
//        if (StringUtils.isNotBlank(source)) {
//            path = path + source + "/";
//        }
//        //获取文件名和后缀
//        FileInfo fileInfo = fileStorageService.of(file)
//                .setPath(path)
//                .setSaveFilename(RandomUtil.randomNumbers(2) + "_" + file.getOriginalFilename()) //随机俩个数字，避免相同文件名时文件名冲突
//                .putAttr("source",source)
//                .upload();
//
//        if (fileInfo == null) {
//            throw new ServiceException("上传文件失败");
//        }
//        return Result.success(fileInfo.getUrl());
//    }
    @SaCheckLogin
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public Result<String> upload(MultipartFile file, String source) {
        String url = null;
        try {
            url = minIOUtils.uploadFile(file);
        } catch (Exception e) {
            log.error("上传文件失败");
            return Result.error("上传文件失败");
        }
        log.info("图片url：{}", url);
        return Result.success(url);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除文件")
    public Result<Boolean> delete(String url) {
        int index = url.lastIndexOf("/");
        String fileName = url.substring(index);
        try {
            minIOUtils.removeFile(fileName);
        } catch (Exception e) {
            log.error("文件删除异常");
            return Result.error("文件删除异常");
        }
        return Result.success();
    }
}
