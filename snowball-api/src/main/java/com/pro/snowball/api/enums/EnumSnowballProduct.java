package com.pro.snowball.api.enums;

import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.snowball.api.model.db.SnowballProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 字典配置 定制
 */
@Getter
@AllArgsConstructor
public enum EnumSnowballProduct implements IEnumToDbEnum<SnowballProduct> {
    _1(BigDecimal.valueOf(100), "SnowballProduct1", null),
    _2(BigDecimal.valueOf(1000), "SnowballProduct2", null),
    ;
//    @ApiModelProperty(value = "编号")
//    @JTDField(uiType = JTDConst.EnumFieldUiType.text, sort = 0)
//    protected Long id;
    @ApiModelProperty("单价")
    private BigDecimal price;
    @ApiModelProperty(value = "弹窗提示文章编号")
    private String openDialogPosterCode;

    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
