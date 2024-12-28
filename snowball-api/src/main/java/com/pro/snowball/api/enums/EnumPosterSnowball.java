package com.pro.snowball.api.enums;

import com.pro.framework.api.enums.EnumToDbEnum;
import com.pro.framework.api.enums.IEnumToDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 路由配置 基础
 */
@Getter
@AllArgsConstructor
@EnumToDbEnum(entityClass = "com.pro.common.module.api.common.model.db.Poster")
public enum EnumPosterSnowball implements IEnumToDbEnum {
//    SnowballProduct1("en-US", "SnowballProduct1", "Winning Rules", null, "SnowballProduct1", "", true),
//    SnowballProduct2("en-US", "SnowballProduct2", "Winning Rules", null, "SnowballProduct2", "", true),
    ;
    private String lang;
    private String code;
    private String title;
    private String subtitle;
    private String content;
    private String remark;
    private Boolean enabled;
}
