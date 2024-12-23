package com.pro.snowball.api.enums;

import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.common.modules.api.dependencies.enums.EnumSysRole;
import com.pro.common.module.api.common.model.db.AuthRoute;
import com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType;
import com.pro.framework.api.enums.EnumOrder;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.framework.javatodb.annotation.JTDField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType.*;
import static com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType.BUTTON;
import static com.pro.common.modules.api.dependencies.enums.EnumSysRole.ADMIN;
import static com.pro.common.modules.api.dependencies.enums.EnumSysRole.AGENT;

/**
 * 定制路由配置
 * 相比基础配置而言,先入库先生效
 */
@Getter
@AllArgsConstructor
@EnumOrder(1)
public enum EnumAuthRouteSnowball implements IEnumToDbEnum<AuthRoute> {
    count(ADMIN, null, MENU, "统计", "/count", "el-icon-s-data", "user,userMoneyRecord", null, null, null, false, 10200, true, null),
    catalog_snowball(ADMIN, null, CATALOG, "滚雪球管理", null, "el-icon-coffee-cup", null, null, null, null, null, 100100, true, null),
//    demo(ADMIN, catalog_snowball, MENU, "项目产品", "/snowball/demo", null, null, null, null, null, null, 100200, true, null),
//    demo_QUERY(ADMIN, demo, BUTTON, "查询", null, null, "demo", null, null, null, null, 100200, true, null),
//    demo_ALL(ADMIN, demo, BUTTON, "管理", null, null, "#ALL#demo", null, null, null, null, 100201, true, null),
//    demo_ALL_AGENT(AGENT, demo, BUTTON, "管理", null, null, "#ALL#demo", null, null, null, null, 100201, true, null),
    executeOrder(ADMIN, catalog_snowball, MENU, "执行订单", "/snowball/executeOrder", null, null, null, null, null, null, 1001200, true, null),
    executeOrder_QUERY(ADMIN, executeOrder, BUTTON, "查询", null, null, "executeOrder", null, null, null, null, 1001200, true, null),
    executeOrder_ALL(ADMIN, executeOrder, BUTTON, "管理", null, null, "#ALL#executeOrder", null, null, null, null, 1001201, true, null),
    executeOrder_ALL_AGENT(AGENT, executeOrder, BUTTON, "管理", null, null, "#ALL#executeOrder", null, null, null, null, 1001201, true, null),
    executeOrderStep(ADMIN, catalog_snowball, MENU, "执行订单的步骤", "/snowball/executeOrderStep", null, null, null, null, null, null, 1001100, true, null),
    executeOrderStep_QUERY(ADMIN, executeOrderStep, BUTTON, "查询", null, null, "executeOrderStep", null, null, null, null, 1001100, true, null),
    executeOrderStep_ALL(ADMIN, executeOrderStep, BUTTON, "管理", null, null, "#ALL#executeOrderStep", null, null, null, null, 1001101, true, null),
    executeOrderStep_ALL_AGENT(AGENT, executeOrderStep, BUTTON, "管理", null, null, "#ALL#executeOrderStep", null, null, null, null, 1001101, true, null),
    executeOrderStepCommand(ADMIN, catalog_snowball, MENU, "执行订单的步骤命令行", "/snowball/executeOrderStepCommand", null, null, null, null, null, null, 1001000, true, null),
    executeOrderStepCommand_QUERY(ADMIN, executeOrderStepCommand, BUTTON, "查询", null, null, "executeOrderStepCommand", null, null, null, null, 1001000, true, null),
    executeOrderStepCommand_ALL(ADMIN, executeOrderStepCommand, BUTTON, "管理", null, null, "#ALL#executeOrderStepCommand", null, null, null, null, 1001001, true, null),
    executeOrderStepCommand_ALL_AGENT(AGENT, executeOrderStepCommand, BUTTON, "管理", null, null, "#ALL#executeOrderStepCommand", null, null, null, null, 1001001, true, null),
    executeStep(ADMIN, catalog_snowball, MENU, "步骤配置", "/snowball/executeStep", null, null, null, null, null, null, 1000900, true, null),
    executeStep_QUERY(ADMIN, executeStep, BUTTON, "查询", null, null, "executeStep", null, null, null, null, 1000900, true, null),
    executeStep_ALL(ADMIN, executeStep, BUTTON, "管理", null, null, "#ALL#executeStep", null, null, null, null, 1000901, true, null),
    executeStep_ALL_AGENT(AGENT, executeStep, BUTTON, "管理", null, null, "#ALL#executeStep", null, null, null, null, 1000901, true, null),
    executeStepCommand(ADMIN, catalog_snowball, MENU, "步骤配置_命令行", "/snowball/executeStepCommand", null, null, null, null, null, null, 1000800, true, null),
    executeStepCommand_QUERY(ADMIN, executeStepCommand, BUTTON, "查询", null, null, "executeStepCommand", null, null, null, null, 1000800, true, null),
    executeStepCommand_ALL(ADMIN, executeStepCommand, BUTTON, "管理", null, null, "#ALL#executeStepCommand", null, null, null, null, 1000801, true, null),
    executeStepCommand_ALL_AGENT(AGENT, executeStepCommand, BUTTON, "管理", null, null, "#ALL#executeStepCommand", null, null, null, null, 1000801, true, null),
    executeStepParamRequired(ADMIN, catalog_snowball, MENU, "步骤配置_需要的参数", "/snowball/executeStepParamRequired", null, null, null, null, null, null, 1000700, true, null),
    executeStepParamRequired_QUERY(ADMIN, executeStepParamRequired, BUTTON, "查询", null, null, "executeStepParamRequired", null, null, null, null, 1000700, true, null),
    executeStepParamRequired_ALL(ADMIN, executeStepParamRequired, BUTTON, "管理", null, null, "#ALL#executeStepParamRequired", null, null, null, null, 1000701, true, null),
    executeStepParamRequired_ALL_AGENT(AGENT, executeStepParamRequired, BUTTON, "管理", null, null, "#ALL#executeStepParamRequired", null, null, null, null, 1000701, true, null),
    executeTemplate(ADMIN, catalog_snowball, MENU, "模板配置", "/snowball/executeTemplate", null, null, null, null, null, null, 1000600, true, null),
    executeTemplate_QUERY(ADMIN, executeTemplate, BUTTON, "查询", null, null, "executeTemplate", null, null, null, null, 1000600, true, null),
    executeTemplate_ALL(ADMIN, executeTemplate, BUTTON, "管理", null, null, "#ALL#executeTemplate", null, null, null, null, 1000601, true, null),
    executeTemplate_ALL_AGENT(AGENT, executeTemplate, BUTTON, "管理", null, null, "#ALL#executeTemplate", null, null, null, null, 1000601, true, null),
    executeTemplateAndStep(ADMIN, catalog_snowball, MENU, "模板配置和步骤配置的关联", "/snowball/executeTemplateAndStep", null, null, null, null, null, null, 1000500, true, null),
    executeTemplateAndStep_QUERY(ADMIN, executeTemplateAndStep, BUTTON, "查询", null, null, "executeTemplateAndStep", null, null, null, null, 1000500, true, null),
    executeTemplateAndStep_ALL(ADMIN, executeTemplateAndStep, BUTTON, "管理", null, null, "#ALL#executeTemplateAndStep", null, null, null, null, 1000501, true, null),
    executeTemplateAndStep_ALL_AGENT(AGENT, executeTemplateAndStep, BUTTON, "管理", null, null, "#ALL#executeTemplateAndStep", null, null, null, null, 1000501, true, null),
    myExecuteGroup(ADMIN, catalog_snowball, MENU, "我的分组", "/snowball/myExecuteGroup", null, null, null, null, null, null, 1000400, true, null),
    myExecuteGroup_QUERY(ADMIN, myExecuteGroup, BUTTON, "查询", null, null, "myExecuteGroup", null, null, null, null, 1000400, true, null),
    myExecuteGroup_ALL(ADMIN, myExecuteGroup, BUTTON, "管理", null, null, "#ALL#myExecuteGroup", null, null, null, null, 1000401, true, null),
    myExecuteGroup_ALL_AGENT(AGENT, myExecuteGroup, BUTTON, "管理", null, null, "#ALL#myExecuteGroup", null, null, null, null, 1000401, true, null),
    myExecuteTemplate(ADMIN, catalog_snowball, MENU, "我的模板", "/snowball/myExecuteTemplate", null, null, null, null, null, null, 1000300, true, null),
    myExecuteTemplate_QUERY(ADMIN, myExecuteTemplate, BUTTON, "查询", null, null, "myExecuteTemplate", null, null, null, null, 1000300, true, null),
    myExecuteTemplate_ALL(ADMIN, myExecuteTemplate, BUTTON, "管理", null, null, "#ALL#myExecuteTemplate", null, null, null, null, 1000301, true, null),
    myExecuteTemplate_ALL_AGENT(AGENT, myExecuteTemplate, BUTTON, "管理", null, null, "#ALL#myExecuteTemplate", null, null, null, null, 1000301, true, null),
    myExecuteTemplateParam(ADMIN, catalog_snowball, MENU, "我的模板的参数", "/snowball/myExecuteTemplateParam", null, null, null, null, null, null, 1000200, true, null),
    myExecuteTemplateParam_QUERY(ADMIN, myExecuteTemplateParam, BUTTON, "查询", null, null, "myExecuteTemplateParam", null, null, null, null, 1000200, true, null),
    myExecuteTemplateParam_ALL(ADMIN, myExecuteTemplateParam, BUTTON, "管理", null, null, "#ALL#myExecuteTemplateParam", null, null, null, null, 1000201, true, null),
    myExecuteTemplateParam_ALL_AGENT(AGENT, myExecuteTemplateParam, BUTTON, "管理", null, null, "#ALL#myExecuteTemplateParam", null, null, null, null, 1000201, true, null),
    remoteServer(ADMIN, catalog_snowball, MENU, "远程服务器", "/snowball/remoteServer", null, null, null, null, null, null, 1000100, true, null),
    remoteServer_QUERY(ADMIN, remoteServer, BUTTON, "查询", null, null, "remoteServer", null, null, null, null, 1000100, true, null),
    remoteServer_ALL(ADMIN, remoteServer, BUTTON, "管理", null, null, "#ALL#remoteServer", null, null, null, null, 1000101, true, null),
    remoteServer_ALL_AGENT(AGENT, remoteServer, BUTTON, "管理", null, null, "#ALL#remoteServer", null, null, null, null, 1000101, true, null),
    module(ADMIN, catalog_snowball, MENU, "模块", "/snowball/module", null, null, null, null, null, null, 1001300, true, null),
    module_QUERY(ADMIN, module, BUTTON, "查询", null, null, "module", null, null, null, null, 1001300, true, null),
    module_ALL(ADMIN, module, BUTTON, "管理", null, null, "#ALL#module", null, null, null, null, 1001301, true, null),
    module_ALL_AGENT(AGENT, module, BUTTON, "管理", null, null, "#ALL#module", null, null, null, null, 1001301, true, null),

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
    private final Boolean showFlag;
    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;


    @Override
    public String getToDbCode() {
        return sysRole + CommonConst.Str.SPLIT + name();
    }
}
