package com.mojian.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@TableName("sys_resource")
@ApiModel(value = "资源表对象")
public class SysResource implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

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

    @ApiModelProperty(value = "状态 0:未通过 1:待审核 2:通过")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
