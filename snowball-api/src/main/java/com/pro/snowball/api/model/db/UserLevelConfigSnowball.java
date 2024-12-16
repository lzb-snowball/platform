package com.pro.snowball.api.model.db;

import com.pro.common.module.api.userlevel.model.db.UserLevelConfig;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JTDTable(
        module = "user",
        entityId = 10003
)
@ApiModel(description = "用户等级配置")
public class UserLevelConfigSnowball extends UserLevelConfig {
}
