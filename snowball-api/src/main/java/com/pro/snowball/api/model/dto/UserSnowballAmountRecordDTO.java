package com.pro.snowball.api.model.dto;

import com.pro.common.module.api.usermoney.model.modelbase.AmountEntityRecordDTO;
import com.pro.framework.api.FrameworkConst;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户项目卡包数量")
public class UserSnowballAmountRecordDTO extends AmountEntityRecordDTO {
    @ApiModelProperty("产品id")
    private Long productId;
    @Override
    public String getEntityKey() {
        return productId + FrameworkConst.Str.split_pound + getUserId();
    }
}
