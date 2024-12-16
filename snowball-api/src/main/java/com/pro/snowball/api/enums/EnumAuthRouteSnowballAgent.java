package com.pro.snowball.api.enums;

import com.pro.common.module.api.common.model.db.AuthRoute;
import com.pro.common.module.api.common.model.enums.EnumAuthRouteType;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.common.modules.api.dependencies.enums.EnumSysRole;
import com.pro.framework.api.enums.IEnumToDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.pro.common.module.api.common.model.enums.EnumAuthRouteType.*;
import static com.pro.common.modules.api.dependencies.enums.EnumSysRole.AGENT;

/**
 * 路由配置 定制
 */
@Getter
@AllArgsConstructor
public enum EnumAuthRouteSnowballAgent implements IEnumToDbEnum<AuthRoute> {
    catalog_snowball(AGENT, null, CATALOG, "项目管理", null, "el-icon-trophy-1", null, null, null, null, true, 100100, null),

//    userSnowballAmount(AGENT, catalog_snowball, MENU, "用户项目卡包数量", "/snowball/userSnowballAmount", null, null, null, null, null, false, 100500, null),
//    userSnowballAmount_QUERY(AGENT, userSnowballAmount, BUTTON, "查询", null, null, "userSnowballAmount", null, null, null, false, 100500, null),
//    userSnowballAmount_ALL(AGENT, userSnowballAmount, BUTTON, "管理", null, null, "#ALL#userSnowballAmount", null, null, null, false, 100501, null),
//    userSnowballAmountRecord(AGENT, catalog_snowball, MENU, "用户项目卡包数量变动", "/snowball/userSnowballAmountRecord", null, null, null, null, null, false, 100600, null),
//    userSnowballAmountRecord_QUERY(AGENT, userSnowballAmountRecord, BUTTON, "查询", null, null, "userSnowballAmountRecord", null, null, null, false, 100600, null),
//    userSnowballAmountRecord_ALL(AGENT, userSnowballAmountRecord, BUTTON, "管理", null, null, "#ALL#userSnowballAmountRecord", null, null, null, false, 100601, null),
    snowballProduct(AGENT, catalog_snowball, MENU, "项目产品配置", "/snowball/snowballProduct", null, null, null, null, null, false, 100200, null),
    snowballProduct_QUERY(AGENT, snowballProduct, BUTTON, "查询", null, null, "snowballProduct,snowballProductRate", null, null, null, false, 100200, null),
    snowballProduct_ALL(AGENT, snowballProduct, BUTTON, "管理", null, null, "#ALL#snowballProduct,#ALL#snowballProductRate", null, null, null, false, 100201, null),

    ;
    private final EnumSysRole sysRole;
    private final EnumAuthRouteSnowballAgent pcode;
    private final EnumAuthRouteType type;
    private final String name;
    private final String componentPath;
    private final String icon;
    private String permissionPaths;
    private final String pic;
    private final String url;
    private final String remark;
    private final Boolean enabled;
    private final Integer sort;
    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;


    @Override
    public String getToDbCode() {
        return sysRole + CommonConst.Str.SPLIT + name();
    }
}
