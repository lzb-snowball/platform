package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "参数可选值")
@JTDTable(entityId = 10016, module = "snowball", sequences = {
        "UNIQUE KEY `uk_modelCode_groupId_myTemplateId_templateId` (`model_code`,`group_id`,`my_template_id`,`template_id`)",
})
public class ExecuteParam extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "我的我的分组")
    @JTDField(entityClass = ExecuteGroup.class, entityClassKey = "id", entityClassTargetProp = "id", notNull = JTDConst.EnumFieldNullType.can_null)
    private String groupId;
    @ApiModelProperty(value = "我的模板")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id", notNull = JTDConst.EnumFieldNullType.can_null)
    private String myTemplateId;
//    @ApiModelProperty(value = "模板Id")
//    @JTDField(entityClass = ExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id", notNull = JTDConst.EnumFieldNullType.can_null)
//    private String templateId;
//    @ApiModelProperty(value = "执行步骤Id")
//    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id", description = "默认不指定则不限制", notNull = JTDConst.EnumFieldNullType.can_null)
//    private String stepId;
//    @ApiModelProperty(value = "执行步骤命令行Id")
//    @JTDField(entityClass = ExecuteStepCommand.class, entityClassKey = "id", entityClassTargetProp = "id", description = "默认不指定则不限制", notNull = JTDConst.EnumFieldNullType.can_null)
//    private String stepCommandId;
    @ApiModelProperty(value = "参数模型")
    @JTDField(entityClass = ExecuteParamModel.class, notEmpty = JTDConst.EnumFieldEmptyType.not_empty)
    private String modelCode;
    @ApiModelProperty(value = "值")
    @JTDField(mainLength = 2048, notEmpty = JTDConst.EnumFieldEmptyType.not_empty)
    private String value;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    @ApiModelProperty(value = "执行前必须编辑")
    private Boolean executeEdit;
    @ApiModelProperty(value = "默认值", notes = "取字符串 或者 值选项的编号")
    @JTDField(notNull = JTDConst.EnumFieldNullType.can_null, mainLength = 2048)
    private String defaultValue;
}
