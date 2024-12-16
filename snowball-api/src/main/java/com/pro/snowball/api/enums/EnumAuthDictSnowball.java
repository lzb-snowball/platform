package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.db.AuthDict;
import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.framework.javatodb.constant.JTDConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.pro.framework.javatodb.constant.JTDConst.EnumFieldUiType.text;
import static com.pro.framework.javatodb.constant.JTDConst.EnumFieldUiType.textarea;

/**
 * 字典配置 定制
 */
@Getter
@AllArgsConstructor
public enum EnumAuthDictSnowball implements IEnumAuthDict, IEnumToDbEnum<AuthDict> {

    SNOWBALL_CONFIG("彩票配置", "", null, text, false, null, null, null, null, null, 1, null),
    SNOWBALL_COMMISSION_PHONES("购买彩票模拟手机号", "1481612552\r\n1592855442\r\n1344137322\r\n1918759265", SNOWBALL_CONFIG, textarea, null, null, null, null, "文本域，多个换行区别", null, null, null),

    ;
    private final String label;
    private final String value;
    private final EnumAuthDictSnowball pcode;
    private final JTDConst.EnumFieldUiType inputType;
    private final Boolean showUser;
    private final Boolean showAdmin;
    private final String enumClass;
    private final Boolean enumClassMultiple;
    private final String remark;
    private final Boolean enabled;
    private final Integer sort;
    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
