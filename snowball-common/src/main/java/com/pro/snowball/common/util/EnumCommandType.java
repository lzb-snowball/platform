package com.pro.snowball.common.util;

import com.pro.framework.api.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 命令类型
 */
@Getter
@AllArgsConstructor
public enum EnumCommandType implements IEnum {
    local("本地"),
    remote("远程"),
    ;

    final String label;

}
