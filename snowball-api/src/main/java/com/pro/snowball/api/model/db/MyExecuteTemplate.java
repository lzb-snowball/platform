package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.api.enums.IEnumToDbDbId;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "我的模板")
@JTDTable(entityId = 10003, module = "snowball")
public class MyExecuteTemplate extends BaseModel implements IConfigClass, IEnumToDbDbId {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "分组")
    @JTDField(entityClass = ExecuteGroup.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long groupId;
    @ApiModelProperty(value = "模板配置")
    @JTDField(entityClass = ExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long templateId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    @ApiModelProperty(value = "最后订单")
    @JTDField(defaultValue = "NULL", notNull = JTDConst.EnumFieldNullType.can_null)
    private Long lastOrderId;
    @ApiModelProperty(value = "最后订单状态")
    @JTDField(defaultValue = "NULL", notNull = JTDConst.EnumFieldNullType.can_null)
    private EnumExecuteOrderState lastOrderState;
    @ApiModelProperty(value = "最后订单状态时间")
    private LocalDateTime lastOrderStateTime;
    @ApiModelProperty(value = "总共几步")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private Integer lastOrderStepNoAll;
    @ApiModelProperty(value = "已完成第几步")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private Integer lastOrderStepNoCurrent;
    @ApiModelProperty(value = "订单成功次数")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private Integer orderSuccessTimes;
    @ApiModelProperty(value = "订单成功总耗时_秒")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private Integer orderSuccessSeconds;
}
