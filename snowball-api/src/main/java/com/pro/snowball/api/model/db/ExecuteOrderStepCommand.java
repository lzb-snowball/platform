package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.constant.JTDConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "执行订单步骤")
public class ExecuteOrderStepCommand extends BaseModel {
    @ApiModelProperty(value = "序号")
    private Integer no;
    @ApiModelProperty(value = "对应订单id")
    //    @JTDField(entityClass = ExecuteOrder.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long orderId;
    @ApiModelProperty(value = "对应订单步骤id")
    //    @JTDField(entityClass = ExecuteOrderStep.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long orderStepId;
    @ApiModelProperty(value = "运行日志")
    @JTDField(type = JTDConst.EnumFieldType.text)
    private String infoContent;
    @ApiModelProperty(value = "错误日志")
    @JTDField(type = JTDConst.EnumFieldType.text)
    private String errorContent;
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "备注")
    private String remark;

}