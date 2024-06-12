package com.dh.mybatisplustestdemo.demos.enumeration;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {

    NORMAL(1, "正常"),
    FREEZE(2, "冻结");

    @EnumValue //mybatis-plus将其写入数据库
    @JsonValue //返回前端时使用这个值返回
    private final Integer code;


    private final String desc;

    UserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
