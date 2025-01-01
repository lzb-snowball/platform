package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.db.AuthDict;
import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.EnumOrder;
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
@EnumOrder(1)
public enum EnumAuthDictSnowball implements IEnumAuthDict, IEnumToDbEnum<AuthDict> {

    SNOWBALL_CONFIG("滚雪球配置", "", null, text, false, null, null, null, null, null, 1, true,null),
    SNOWBALL_COMMISSION_PHONES("购买滚雪球模拟手机号", "1481612552\r\n1592855442\r\n1344137322\r\n1918759265", SNOWBALL_CONFIG, textarea, null, null, null, null, "文本域，多个换行区别", null, null, true,null),
    SYSTEM_CONFIG("系统配置", "", null, text, null, null, null, null, null, null, 0, true,null),
    FILE_UPLOAD_DOMAIN("文件服务器地址", "http://localhost:8091/api", SYSTEM_CONFIG, text, true, null, "", false, "", null, 6, true, null),

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
    private final Boolean showFlag;
    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
