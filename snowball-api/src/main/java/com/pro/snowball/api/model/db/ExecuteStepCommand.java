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
@ApiModel(description = "步骤配置_命令行")
@JTDTable(entityId = 10008, module = "snowball")
public class ExecuteStepCommand extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "步骤配置")
    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long stepId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "内容")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.xml)
    private String content;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "在远程服务器执行", notes = "注:scp一般在本地执行-虽然传输到远程服务器")
    private Boolean remoteServerFlag;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
