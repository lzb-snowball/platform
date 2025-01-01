//package com.pro.snowball.api.enums;
//
//import com.pro.common.module.api.common.model.db.AuthRoute;
//import com.pro.common.modules.api.dependencies.CommonConst;
//import com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType;
//import com.pro.common.modules.api.dependencies.enums.EnumSysRole;
//import com.pro.framework.api.enums.EnumOrder;
//import com.pro.framework.api.enums.IEnumToDbEnum;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//import static com.pro.common.modules.api.dependencies.enums.EnumAuthRouteType.*;
//import static com.pro.common.modules.api.dependencies.enums.EnumSysRole.ADMIN;
//import static com.pro.common.modules.api.dependencies.enums.EnumSysRole.AGENT;
//
///**
// * 定制路由配置
// * 相比基础配置而言,先入库先生效
// */
//@Getter
//@AllArgsConstructor
//@EnumOrder(1)
//public enum EnumAuthRouteSnowballBak implements IEnumToDbEnum<AuthRoute> {
//    count(ADMIN, null, MENU, "统计", "/count", "el-icon-s-data", "user,userMoneyRecord", null, null, null, false, 10200, true, null),
//    catalog_snowball(ADMIN, null, CATALOG, "滚雪球管理", null, "el-icon-coffee-cup", null, null, null, null, null, 100100, true, null),
////    demo(ADMIN, catalog_snowball, MENU, "项目产品", "/snowball/demo", null, null, null, null, null, null, 100200, true, null),
////    demo_QUERY(ADMIN, demo, BUTTON, "查询", null, null, "demo", null, null, null, null, 100200, true, null),
////    demo_ALL(ADMIN, demo, BUTTON, "管理", null, null, "#ALL#demo", null, null, null, null, 100201, true, null),
////    demo_ALL_AGENT(AGENT, demo, BUTTON, "管理", null, null, "#ALL#demo", null, null, null, null, 100201, true, null),
//    executeOrder(ADMIN, catalog_snowball, MENU, "执行订单", "/snowball/executeOrder", null, null, null, null, null, null, 1001200, true, null),
//    executeOrder_QUERY(ADMIN, executeOrder, BUTTON, "查询", null, null, "executeOrder", null, null, null, null, 1001200, true, null),
//    executeOrder_ALL(ADMIN, executeOrder, BUTTON, "管理", null, null, "#ALL#executeOrder", null, null, null, null, 1001201, true, null),
//    executeOrder_ALL_AGENT(AGENT, executeOrder, BUTTON, "管理", null, null, "#ALL#executeOrder", null, null, null, null, 1001201, true, null),
//    executeStep(ADMIN, catalog_snowball, MENU, "步骤配置", "/snowball/executeStep", null, null, null, null, null, null, 1000900, true, null),
//    executeStep_QUERY(ADMIN, executeStep, BUTTON, "查询", null, null, "executeStep", null, null, null, null, 1000900, true, null),
//    executeStep_ALL(ADMIN, executeStep, BUTTON, "管理", null, null, "#ALL#executeStep", null, null, null, null, 1000901, true, null),
//    executeStep_ALL_AGENT(AGENT, executeStep, BUTTON, "管理", null, null, "#ALL#executeStep", null, null, null, null, 1000901, true, null),
//    executeStepCommand(ADMIN, catalog_snowball, MENU, "步骤配置_命令行", "/snowball/executeStepCommand", null, null, null, null, null, null, 1000800, true, null),
//    executeStepCommand_QUERY(ADMIN, executeStepCommand, BUTTON, "查询", null, null, "executeStepCommand", null, null, null, null, 1000800, true, null),
//    executeStepCommand_ALL(ADMIN, executeStepCommand, BUTTON, "管理", null, null, "#ALL#executeStepCommand", null, null, null, null, 1000801, true, null),
//    executeStepCommand_ALL_AGENT(AGENT, executeStepCommand, BUTTON, "管理", null, null, "#ALL#executeStepCommand", null, null, null, null, 1000801, true, null),
//    executeTemplate(ADMIN, catalog_snowball, MENU, "模板配置", "/snowball/executeTemplate", null, null, null, null, null, null, 1000600, true, null),
//    executeTemplate_QUERY(ADMIN, executeTemplate, BUTTON, "查询", null, null, "executeTemplate", null, null, null, null, 1000600, true, null),
//    executeTemplate_ALL(ADMIN, executeTemplate, BUTTON, "管理", null, null, "#ALL#executeTemplate", null, null, null, null, 1000601, true, null),
//    executeTemplate_ALL_AGENT(AGENT, executeTemplate, BUTTON, "管理", null, null, "#ALL#executeTemplate", null, null, null, null, 1000601, true, null),
//    executeTemplateAndStep(ADMIN, catalog_snowball, MENU, "模板配置和步骤配置的关联", "/snowball/executeTemplateAndStep", null, null, null, null, null, null, 1000500, true, null),
//    executeTemplateAndStep_QUERY(ADMIN, executeTemplateAndStep, BUTTON, "查询", null, null, "executeTemplateAndStep", null, null, null, null, 1000500, true, null),
//    executeTemplateAndStep_ALL(ADMIN, executeTemplateAndStep, BUTTON, "管理", null, null, "#ALL#executeTemplateAndStep", null, null, null, null, 1000501, true, null),
//    executeTemplateAndStep_ALL_AGENT(AGENT, executeTemplateAndStep, BUTTON, "管理", null, null, "#ALL#executeTemplateAndStep", null, null, null, null, 1000501, true, null),
//    executeGroup(ADMIN, catalog_snowball, MENU, "我的分组", "/snowball/executeGroup", null, null, null, null, null, null, 1000400, true, null),
//    executeGroup_QUERY(ADMIN, executeGroup, BUTTON, "查询", null, null, "executeGroup", null, null, null, null, 1000400, true, null),
//    executeGroup_ALL(ADMIN, executeGroup, BUTTON, "管理", null, null, "#ALL#executeGroup", null, null, null, null, 1000401, true, null),
//    executeGroup_ALL_AGENT(AGENT, executeGroup, BUTTON, "管理", null, null, "#ALL#executeGroup", null, null, null, null, 1000401, true, null),
//    myExecuteTemplate(ADMIN, catalog_snowball, MENU, "我的模板", "/snowball/myExecuteTemplate", null, null, null, null, null, null, 1000300, true, null),
//    myExecuteTemplate_QUERY(ADMIN, myExecuteTemplate, BUTTON, "查询", null, null, "myExecuteTemplate", null, null, null, null, 1000300, true, null),
//    myExecuteTemplate_ALL(ADMIN, myExecuteTemplate, BUTTON, "管理", null, null, "#ALL#myExecuteTemplate", null, null, null, null, 1000301, true, null),
//    myExecuteTemplate_ALL_AGENT(AGENT, myExecuteTemplate, BUTTON, "管理", null, null, "#ALL#myExecuteTemplate", null, null, null, null, 1000301, true, null),
//    executeParam(ADMIN, catalog_snowball, MENU, "参数值", "/snowball/executeParam", null, null, null, null, null, null, 1001600, true, null),
//    executeParam_QUERY(ADMIN, executeParam, BUTTON, "查询", null, null, "executeParam", null, null, null, null, 1001600, true, null),
//    executeParam_ALL(ADMIN, executeParam, BUTTON, "管理", null, null, "#ALL#executeParam", null, null, null, null, 1001601, true, null),
//    executeParam_ALL_AGENT(AGENT, executeParam, BUTTON, "管理", null, null, "#ALL#executeParam", null, null, null, null, 1001601, true, null),
//    executeParamField(ADMIN, catalog_snowball, MENU, "参数模型属性", "/snowball/executeParamField", null, null, null, null, null, null, 1001500, true, null),
//    executeParamField_QUERY(ADMIN, executeParamField, BUTTON, "查询", null, null, "executeParamField", null, null, null, null, 1001500, true, null),
//    executeParamField_ALL(ADMIN, executeParamField, BUTTON, "管理", null, null, "#ALL#executeParamField", null, null, null, null, 1001501, true, null),
//    executeParamField_ALL_AGENT(AGENT, executeParamField, BUTTON, "管理", null, null, "#ALL#executeParamField", null, null, null, null, 1001501, true, null),
//    executeParamModel(ADMIN, catalog_snowball, MENU, "参数模型", "/snowball/executeParamModel", null, null, null, null, null, null, 1001400, true, null),
//    executeParamModel_QUERY(ADMIN, executeParamModel, BUTTON, "查询", null, null, "executeParamModel", null, null, null, null, 1001400, true, null),
//    executeParamModel_ALL(ADMIN, executeParamModel, BUTTON, "管理", null, null, "#ALL#executeParamModel", null, null, null, null, 1001401, true, null),
//    executeParamModel_ALL_AGENT(AGENT, executeParamModel, BUTTON, "管理", null, null, "#ALL#executeParamModel", null, null, null, null, 1001401, true, null),
//
//    ;
//    private final EnumSysRole sysRole;
//    private final EnumAuthRouteSnowballBak pcode;
//    private final EnumAuthRouteType type;
//    private final String name;
//    private final String componentPath;
//    private final String icon;
//    private final String permissionPaths;
//    private final String pic;
//    private final String url;
//    private final String remark;
//    private final Boolean enabled;
//    private final Integer sort;
//    private final Boolean showFlag;
//    /**
//     * 统一重置修改掉,这个配置时间以前的,旧的配置
//     */
//    private final String forceChangeTime;
//
//
//    @Override
//    public String getToDbCode() {
//        return sysRole + CommonConst.Str.SPLIT + name();
//    }
//}
