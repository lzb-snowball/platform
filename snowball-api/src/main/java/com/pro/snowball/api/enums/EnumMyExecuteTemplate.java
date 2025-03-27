package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import com.pro.snowball.api.model.db.ExecuteGroup;
import com.pro.snowball.api.model.db.ExecuteTemplate;
import com.pro.snowball.api.model.db.MyExecuteTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum EnumMyExecuteTemplate implements IEnumToDbEnum<MyExecuteTemplate> {

    _1("分组1 更新-jar", 1L, 1L, "", 100, true, null, null, null, 0, 0, null),
    _2("分组1 更新-ui", 1L, 2L, "", 100, true, null, null, null, 0, 0, null),
    _3("分组1 更新-jar-lib", 1L, 3L, "", 100, true, null, null, null, 0, 0, null);

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
    @ApiModelProperty(value = "最后订单状态时间")
    private LocalDateTime lastOrderStateTime;
    @ApiModelProperty(value = "最后订单状态")
    private EnumExecuteOrderState lastOrderState;
    @ApiModelProperty(value = "最后订单")
    private Long lastOrderId;
    @ApiModelProperty(value = "订单成功次数")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private Integer orderSuccessTimes;
    @ApiModelProperty(value = "订单成功总耗时_秒")
    @JTDField(uiType = JTDConst.EnumFieldUiType.hide)
    private Integer orderSuccessSeconds;

    private final String forceChangeTime;
}
