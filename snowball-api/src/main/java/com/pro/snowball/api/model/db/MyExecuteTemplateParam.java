package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.snowball.api.enums.EnumExecuteStepParamInputType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "我的模板的参数")
@JTDTable(entityId = 10002, module = "snowball")
public class MyExecuteTemplateParam extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "我的模板")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long myTemplateId;
    @ApiModelProperty(value = "执行步骤Id")
    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id", description = "默认不指定则不限制")
    private String stepId;
    @ApiModelProperty(value = "执行步骤命令行Id")
    @JTDField(entityClass = ExecuteStepCommand.class, entityClassKey = "id", entityClassTargetProp = "id", description = "默认不指定则不限制")
    private String stepCommandId;
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "固定值")
    private String value;
    @ApiModelProperty(value = "默认值")
    private String defaultValue;
    @ApiModelProperty(value = "输入类型")
    @JTDField(description = "如果 ExecuteStepParamRequired 中定义了type=SERVER则只能选择服务器了")
    private EnumExecuteStepParamInputType inputType;

    @ApiModelProperty(value = "自定义选项值")
    @JTDField(description = "例如 user,admin,agent")
    private String selectionCodes;
    @ApiModelProperty(value = "自定义选项名称")
    @JTDField(description = "例如 用户端,管理端,代理端")
    private String selectionLabels;
    @ApiModelProperty(value = "选项值_缩小选择范围")
    @JTDField(description = "例如 服务器id1,服务器id2 只能选择这两台")
    private String narrowYourSelection;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
