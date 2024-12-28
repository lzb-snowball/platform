package com.pro.snowball.api.enums;

import com.pro.framework.api.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行订单状态
 */
@Getter
@AllArgsConstructor
public enum EnumExecuteOrderState implements IEnum {
//    INIT("初始化"),
    DOING("运行中"),        // 后台开始
    SUCCESS("运行成功"),    // 自动成功
    FAIL("错误终止"),       // 错误终止
    STOP("手动停止"),       // 手动停止
    ;
    private final String label;
}
