package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.common.modules.api.dependencies.model.classes.IUserRecordClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "执行订单的步骤命令行")
@JTDTable(entityId = 10010, module = "snowball")
public class ExecuteOrderStepCommand extends BaseModel implements IUserRecordClass {
    @ApiModelProperty(value = "序号")
    private Integer no;
    @ApiModelProperty(value = "订单id")
    //    @JTDField(entityClass = ExecuteOrder.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long orderId;
    @ApiModelProperty(value = "订单步骤id")
    //    @JTDField(entityClass = ExecuteOrderStep.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long orderStepId;
    @ApiModelProperty(value = "执行配置内容")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.xml)
    private String contentCfg;
    @ApiModelProperty(value = "执行具体内容")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.xml)
    private String content;
    @ApiModelProperty(value = "运行日志")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.textarea)
    private String infoContent;
    @ApiModelProperty(value = "错误日志")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.textarea)
    private String errorContent;
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "备注")
    private String remark;


    @Override
    public Long getUserId() {
        return 0L;
    }

    @Override
    public void setUserId(Long userId) {

    }
}
