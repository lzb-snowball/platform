package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.classes.BaseConfigModel;
import com.pro.framework.api.enums.IEnumToDbDbId;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JTDTable(
        module = "snowball",
        entityId = 10007
)
@ApiModel(description = "刮刮乐产品")
public class SnowballProduct extends BaseConfigModel implements IEnumToDbDbId {
    @ApiModelProperty(value = "编号")
    @JTDField(uiType = JTDConst.EnumFieldUiType.text, sort = 0)
    protected Long id;
    @ApiModelProperty("单价")
    private BigDecimal price;
    @ApiModelProperty(value = "弹窗提示文章编号")
    private String openDialogPosterCode;
}
