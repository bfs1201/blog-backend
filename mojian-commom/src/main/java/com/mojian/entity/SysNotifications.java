package com.mojian.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@TableName("sys_notifications")
@ApiModel(value = "消息通知表对象")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysNotifications implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "通知的唯一标识，自增主键")
    private Long id;

    @ApiModelProperty(value = "推送用户id")
    private Long userId;

    @ApiModelProperty(value = "来自用户id")
    private Long fromUserId;

    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "通知的类型，如 system、comment、like 等")
    private String type;

    @ApiModelProperty(value = "通知的标题")
    private String title;

    @ApiModelProperty(value = "通知的具体内容")
    private String message;

    @ApiModelProperty(value = "标记通知是否已读，0 表示未读，1 表示已读")
    private Integer isRead;

    @ApiModelProperty(value = "通知关联的链接，可为空")
    private String link;

    @ApiModelProperty(value = "通知的创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
