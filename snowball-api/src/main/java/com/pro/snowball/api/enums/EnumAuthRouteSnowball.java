package com.pro.snowball.api.enums;

import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.common.modules.api.dependencies.enums.EnumSysRole;
import com.pro.common.module.api.common.model.db.AuthRoute;
import com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType;
import com.pro.framework.api.enums.IEnumToDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType.*;
import static com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType.BUTTON;
import static com.pro.common.modules.api.dependencies.enums.EnumSysRole.ADMIN;
import static com.pro.common.modules.api.dependencies.enums.EnumSysRole.AGENT;

/**
 * 路由配置 定制
 */
@Getter
@AllArgsConstructor
public enum EnumAuthRouteSnowball implements IEnumToDbEnum<AuthRoute> {
    catalog_snowball(ADMIN, null, CATALOG, "项目管理", null, "el-icon-trophy-1", null, null, null, null, null, 100100, null),

    snowballProduct(ADMIN, catalog_snowball, MENU, "项目产品配置", "/snowball/snowballProduct", null, null, null, null, null, null, 100200, null),
    snowballProduct_QUERY(ADMIN, snowballProduct, BUTTON, "查询", null, null, "snowballProduct,snowballProductRate", null, null, null, null, 100200, null),
    snowballProduct_ALL(ADMIN, snowballProduct, BUTTON, "管理", null, null, "#ALL#snowballProduct,#ALL#snowballProductRate", null, null, null, null, 100201, null),
    snowballProduct_ALL_AGENT(AGENT, snowballProduct, BUTTON, "管理", null, null, "#ALL#snowballProduct,#ALL#snowballProductRate", null, null, null, null, 100201, null),

    ;
    private final EnumSysRole sysRole;
    private final EnumAuthRouteSnowball pcode;
    private final EnumAuthRouteType type;
    private final String name;
    private final String componentPath;
    private final String icon;
    private final String permissionPaths;
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