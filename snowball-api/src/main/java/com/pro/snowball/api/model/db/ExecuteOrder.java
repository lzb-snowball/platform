package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IUserOrderClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "执行订单")
@JTDTable(entityId = 10012, module = "snowball")
public class ExecuteOrder extends BaseModel implements IUserOrderClass {
//    @ApiModelProperty(value = "订单编号")
//    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
//    private String no;
    @ApiModelProperty(value = "我的模板")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    // 数据量太大了,暂时不做显性关联
    private Long myTemplateId;
    @ApiModelProperty(value = "模板Id")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    // 数据量太大了,暂时不做显性关联
    private Long templateId;
    //    @ApiModelProperty(value = "最后运行日志")
//    @JTDField(mainLength = 2048)
//    private String infoContent;
//    @ApiModelProperty(value = "最后错误日志")
//    @JTDField(mainLength = 2048)
//    private String errorContent;
    @ApiModelProperty(value = "状态")
    private EnumExecuteOrderState state;
    @ApiModelProperty(value = "状态更变时间")
    private LocalDateTime stateTime;
    @ApiModelProperty(value = "总共几步")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private BigDecimal stepNoAll;
    @ApiModelProperty(value = "已完成第几步")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private BigDecimal stepNoCurrent;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "管理员Id")
    @JTDField(entityName = "admin", entityClassKey = "id", entityClassTargetProp = "id", entityClassLabel = "username")
    // 数据量太大了,暂时不做显性关联
    private Long adminId;
    @ApiModelProperty(value = "基础日志文件")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide, mainLength = 1024)
    private String logFileFull;
    @ApiModelProperty(value = "错误日志文件")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide, mainLength = 1024)
    private String logFileError;
    @ApiModelProperty(value = "基础日志文件全路径")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide, mainLength = 1024)
    private String logFileFullInner;
    @ApiModelProperty(value = "错误日志文件全路径")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide, mainLength = 1024)
    private String logFileErrorInner;
    @ApiModelProperty(value = "工作空间全路径")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide, mainLength = 1024)
    private String filePathsWorkspaceInner;

    @ApiModelProperty(value = "操作类型", notes = "base: 基础增删改 其他类型自定义")
    transient private String optType = "base";

    @ApiModelProperty(value = "输入的执行参数")
    transient private Map<String, Object> inputParamMap;
    @ApiModelProperty(value = "订单执行命令")
    transient private List<ExecuteOrderStepCommand> orderStepCommands;
    @ApiModelProperty(value = "订单执行参数")
    transient private Map<String, ExecuteParam> executeParamMap;

    @Override
    public Long getUserId() {
        return 0L;
    }

    @Override
    public void setUserId(Long userId) {

    }
}
