package com.pro.snowball.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 参数输入类型
 */
@Getter
@AllArgsConstructor
public enum EnumExecuteStepParamInputType {

    SERVER("选择服务器列表"),
    FIX("配置固定值"),
    INPUT_TEXT("自定义选项"),
    INPUT_NUMBER("输入变量"),

    ;
    private final String label;
}
