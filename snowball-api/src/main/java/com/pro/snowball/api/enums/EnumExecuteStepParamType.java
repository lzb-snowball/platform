package com.pro.snowball.api.enums;

import com.pro.framework.api.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 参数输入类型
 */
@Getter
@AllArgsConstructor
public enum EnumExecuteStepParamType implements IEnum {
    SERVER("服务器完整信息"),
    TEXT("文本"),
//    NUMBER("数字"),
    ;
    private final String label;
}
