package com.mojian.vo.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: quequnlong
 * @date: 2025/3/12
 * @description:
 */
@Data
public class SysResourceVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "资源名")
    private String name;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "下载量")
    private Integer downloads;

    @ApiModelProperty(value = "是否免费")
    private Integer isFree;

    @ApiModelProperty(value = "付费方式")
    private Integer payType;

    @ApiModelProperty(value = "网盘地址")
    private String panPath;

    @ApiModelProperty(value = "网盘验证码")
    private String panCode;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
