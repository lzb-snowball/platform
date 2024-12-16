package com.pro.snowball.api.enums;

import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.snowball.api.model.db.UserLevelConfigSnowball;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典配置 定制
 */
@Getter
@AllArgsConstructor
public enum EnumUserLevelConfigSnowball implements IEnumToDbEnum<UserLevelConfigSnowball> {
    _1(1L, " ", 100, "", null),
    ;
    private final Long id;
    private final String name;
    private final Integer sort;
    private final String remark;

    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
