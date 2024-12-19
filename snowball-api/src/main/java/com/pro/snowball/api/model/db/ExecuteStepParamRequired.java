package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.enums.EnumExecuteStepParamInputType;
import com.pro.snowball.api.enums.EnumExecuteStepParamType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "步骤配置_需要的参数")
@JTDTable(entityId = 10007, module = "snowball")
public class ExecuteStepParamRequired extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "步骤配置")
    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long stepId;
    @ApiModelProperty(value = "步骤配置_命令行")
    @JTDField(entityClass = ExecuteStepCommand.class, entityClassKey = "id", entityClassTargetProp = "id", notNull = JTDConst.EnumFieldNullType.can_null)
    private String commandId;
    @ApiModelProperty(value = "参数编号")
    private String code;
    @ApiModelProperty(value = "参数名称")
    private String name;
    @ApiModelProperty(value = "是否是列表")
    @JTDField(defaultValue = "1")
    private Boolean isList;
    @ApiModelProperty(value = "每个值的类型")
    private EnumExecuteStepParamType type;
    @ApiModelProperty(value = "默认值")
    @JTDField(mainLength = 2048)
    private String defaultValue;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
