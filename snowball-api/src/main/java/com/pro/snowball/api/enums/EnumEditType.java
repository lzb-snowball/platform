package com.pro.snowball.api.enums;

import com.pro.framework.api.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 编辑类型
 */
/**
 * 编辑类型
 */
@Getter
@AllArgsConstructor
public enum EnumEditType implements IEnum {

    CAN_EDIT("可以编辑"),
    MUST_EDIT("必须编辑"),
    CANNOT_EDIT("不能编辑");

    private final String label;
}
