package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import lombok.Data;

@Data
public class Demo extends BaseModel {
    private Boolean enabled;
    private Integer sort;
    private Long userId;
}
