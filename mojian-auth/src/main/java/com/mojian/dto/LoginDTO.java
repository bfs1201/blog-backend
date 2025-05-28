package com.mojian.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "登录参数")
public class LoginDTO {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "登录来源 PC ADMIN")
    private String source;

    @NotBlank(message = "验证码nonceStr不能为空")
    private String nonceStr;

    @NotBlank(message = "验证码value不能为空")
    private String value;
}
