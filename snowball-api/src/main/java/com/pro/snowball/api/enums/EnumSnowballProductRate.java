package com.pro.snowball.api.enums;

import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.snowball.api.model.db.SnowballProductRate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 字典配置 定制
 */
@Getter
@AllArgsConstructor
public enum EnumSnowballProductRate implements IEnumToDbEnum<SnowballProductRate> {
    _101(1L, "20,0,0,0", null),
    _102(1L, "10,0,0,0", null),
    _103(1L, "4,0,0,0", null),
    _104(1L, "2,0,0,0", null),
    _105(1L, "1,0,0,0", null),
    _201(2L, "20,0,0,0", null),
    _202(2L, "10,0,0,0", null),
    _203(2L, "4,0,0,0", null),
    _204(2L, "2,0,0,0", null),
    _205(2L, "1,0,0,0", null),
    ;
    //    protected Long id;
    @ApiModelProperty("刮刮乐产品")
    private Long productId;
    @ApiModelProperty("百分比概率")
    private String rate;
    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
