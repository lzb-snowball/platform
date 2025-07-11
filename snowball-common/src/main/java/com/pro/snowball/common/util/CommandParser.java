package com.pro.snowball.common.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.*;
import java.util.regex.*;

public class CommandParser {



    public static List<Command> parseCommands(String content) {
        List<Command> commands = new ArrayList<>();

        // 正则匹配 remote 块
        Pattern remoteCmdPattern = Pattern.compile("<remote server=\"(.*?)\">(.*?)</remote>", Pattern.DOTALL);
        Matcher remoteMatcher = remoteCmdPattern.matcher(content);

        // 按 remote 分割代码
        int lastMatchEnd = 0;
        while (remoteMatcher.find()) {
            // 提取 remote 块之前的部分作为 local
            if (lastMatchEnd < remoteMatcher.start()) {
                String localCode = content.substring(lastMatchEnd, remoteMatcher.start()).trim();
                if (!localCode.isEmpty()) {
                    commands.add(createLocalCommand(localCode));
                }
            }

            // 提取 remote 块
            String serverParam = remoteMatcher.group(1);
            String cmdContent = remoteMatcher.group(2).trim();
            commands.add(createRemoteCommand(serverParam, cmdContent));

            lastMatchEnd = remoteMatcher.end();
        }

        // 处理最后一段可能是 local 的代码
        if (lastMatchEnd < content.length()) {
            String localCode = content.substring(lastMatchEnd).trim();
            if (!localCode.isEmpty()) {
                commands.add(createLocalCommand(localCode));
            }
        }

        return commands;
    }

    private static Command createLocalCommand(String cmd) {
        return new Command(EnumCommandType.local, cmd, null);
    }

    private static Command createRemoteCommand(String serverParam, String cmdContent) {
        // 解析 JSON 格式的 server 参数
        return new Command(EnumCommandType.remote, cmdContent, new JSONObject(serverParam));
    }


}
