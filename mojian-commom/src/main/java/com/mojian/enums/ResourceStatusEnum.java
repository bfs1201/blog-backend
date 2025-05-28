package com.mojian.enums;


import lombok.Data;

/**
 * @author: quequnlong
 * @date: 2025/3/12
 * @description:
 */
public enum ResourceStatusEnum {
    Fail(0, "未通过"),

    AUDIT(1, "待审核"),

    PASS(2, "通过");

    private final Integer code;

    private final String desc;

    ResourceStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
