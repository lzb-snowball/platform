import com.pro.snowball.common.service.xmlparse.XmlParser;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParserTest {

    /**
     * 当前服务器需要安装
     * maven
     * git git账号权限
     * 服务器信息 (私钥)
     *
     */
    @SneakyThrows
    public static void main(String[] args) {
        // 模拟从 XML 中提取 SQL 模板
        String textTemplate = Files.readString(Path.of("/Users/zubin/IdeaProjects/snowball/platform/snowball-common/src/test/resources/run2.xml"));

        // 模拟输入的参数
        Map<String, Object> params = new HashMap<>();
        params.put("platform", "snowball");
        Map<String, Object> secrets = new HashMap<>();
        secrets.put("SERVER_PASSWORD", "aaaaaaaaa");
        secrets.put("SERVER_IP", "192.168.1.1");
        params.put("secrets", secrets);
        params.put("repositories", List.of(
                "git@github.com:lzb-framework/framework.git",
                "git@github.com:lzb-parent/parent.git",
                "git@github.com:lzb-snowball/platform.git"
        ));
        params.put("modules", Arrays.asList("user"));
//        params.put("modules", Arrays.asList("user", "admin", "agent", "libs"));

        // 解析 SQL 模板 类似 MyBatisTemplate
        String finalText = XmlParser.parseTextTemplate(textTemplate, params);
        System.out.println("finalText: \n" + finalText);

        new DebugLocalCommand().executeLocalCommands(new String[]{"cd /Users/zubin/snowball-workspace",finalText});
//        // 解析模板
//        String jsonArray = convertTemplateToJsonArray(finalText);
//
//        // 打印输出
//        System.out.println("Generated JSON Array: \n" + jsonArray);
//        Path path = Path.of("/Users/zubin/IdeaProjects/snowball/platform/snowball-common/src/test/resources/run.json");
//        Files.write(path, jsonArray.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // 替换转义字符
    private static String replaceEscapeCharacters(String text) {
        return text.replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&apos;", "'")
                .replace("\n", "  ")
                ;
    }

    public static String convertTemplateToJsonArray(String template) {
        // 清除掉注释部分
        template = template.replaceAll("<!--.*?-->", "").trim();

        // 去掉最外层的 <template> 标签
        template = template.replaceAll("^<template>", "").replaceAll("</template>$", "").trim();

        // 确保整个内容是一个 JSON 数组的结构
        // 将每个 { } 作为数组的一个元素
//        template = template.replaceAll("(?<=\\})\\s*,\\s*(?=\\{)", "},\n{");

        return replaceEscapeCharacters(template);
    }
}
