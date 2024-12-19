package com.pro.snowball.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行订单状态
 */
@Getter
@AllArgsConstructor
public enum EnumExecuteOrderState {
    SUCCESS("成功"),
    DOING("进行中"),
    FAIL("失败"),
    STOP("手动停止"),
    ;
    private final String label;
}
