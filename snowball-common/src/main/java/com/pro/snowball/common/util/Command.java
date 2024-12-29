package com.pro.snowball.common.util;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    private EnumCommandType cmdType;
    private String cmdContent;
    private JSONObject params;
}