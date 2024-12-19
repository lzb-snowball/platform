package com.pro.snowball.common.service.xmlparse;

import com.pro.snowball.common.service.xmlparse.condition.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {

    // 创建 ConditionContext 实例来管理不同的条件解析器
    private static final ConditionContext conditionContext = new ConditionContext();

    static {
        // 注册各种条件解析策略
        conditionContext.addEvaluator("==", new EqualityConditionEvaluator());
        conditionContext.addEvaluator("!=", new InequalityConditionEvaluator());
        conditionContext.addEvaluator(">", new ComparisonConditionEvaluator());
        conditionContext.addEvaluator("<", new ComparisonConditionEvaluator());
    }

    // 解析 if 标签
    public static String applyIfCondition(String textTemplate, Map<String, Object> params) {
        // 替换 #{secrets.SERVER_PASSWORD} 等变量
        textTemplate = replaceVariables(textTemplate, params);

        // 正则匹配 <if> 标签
        Pattern pattern = Pattern.compile("<if test=\"(.*?)\">(.*?)</if>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(textTemplate);

        StringBuffer resultText = new StringBuffer();
        while (matcher.find()) {
            String condition = matcher.group(1);  // 获取 test 条件
            String content = matcher.group(2);    // 获取 if 标签中的内容

            // 使用 ConditionContext 来评估条件
            boolean conditionResult = conditionContext.evaluate(condition, params);

            // 如果条件满足，保留内容，否则删除
            if (conditionResult) {
                matcher.appendReplacement(resultText, beforeReplace(content));
            } else {
                matcher.appendReplacement(resultText, "");
            }
        }
//        if (textTemplate.contains("print")) {
//            int i = 0;
//        }
        matcher.appendTail(resultText);
        return resultText.toString();
    }

    private static String beforeReplace(String content) {
        return content.replaceAll("\\$", "\\\\\\$").replaceAll("\"", "\\\\\"");
    }

    public static void main(String[] args) {
        String str = "  <if test=\"module != 'libs'\">  {  \"type\":\"cmd\",  \"params\":{  \"cmd\":\"cd /project/snowball && ps aux | grep 'snowball-user.jar' | awk '{print $2}' | xargs kill -9 || true && java -Xms1024m -Xmx1024m -Dloader.path=/project/snowball/lib -jar -Dspring.profiles.active=prod,platform /project/snowball/snowball-user.jar \"  }  },  </if>  ";
        HashMap<String, Object> params = new HashMap<>();
        params.put("module", "user");
        System.out.println(applyIfCondition(str, params));
    }

    // 替换变量 #{secrets.SERVER_PASSWORD} 等
    public static String replaceVariables(String textTemplate, Map<String, Object> params) {
        // 正则匹配 #{变量} 格式
        Pattern pattern = Pattern.compile("#\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(textTemplate);

        StringBuffer resultText = new StringBuffer();
        while (matcher.find()) {
            String variablePath = matcher.group(1).trim();  // 获取变量路径
            Object variableValue = resolve(variablePath, params);  // 使用解析器解析变量路径

            // 如果找到变量值，替换为值，否则保持原样
            if (variableValue != null) {
                matcher.appendReplacement(resultText, beforeReplace(variableValue.toString()));
            } else {
                matcher.appendReplacement(resultText, beforeReplace(matcher.group(0)));  // 保持原样
            }
        }
        matcher.appendTail(resultText);
        return resultText.toString();
    }

    public static String applyForEach(String textTemplate, Map<String, Object> params) {
        // 更新正则，使 open, close, separator 都为可选项
        Pattern pattern = Pattern.compile(
                "<foreach\\s+collection=\"(.*?)\"\\s+item=\"(.*?)\"(?:\\s*separator=\"([^\"]*)\")?" +
                        "(?:\\s*open=\"([^\"]*)\")?" +
                        "(?:\\s*close=\"([^\"]*)\")?" +
                        "\\s*>(.*?)</foreach>", Pattern.DOTALL);

        Matcher matcher = pattern.matcher(textTemplate);

        StringBuffer resultText = new StringBuffer();
        while (matcher.find()) {
            // 捕获属性
            String collectionName = matcher.group(1); // collection
            String itemName = matcher.group(2); // item
            String separator = matcher.group(3); // separator
            String open = matcher.group(4);  // open
            String close = matcher.group(5); // close
            String content = matcher.group(6); // <foreach> 标签中的内容

            separator = separator == null ? "" : separator;
            open = open == null ? "" : open;
            close = close == null ? "" : close;
            content = content == null ? "" : content;

            // 替换模板中的变量 #{secrets.SERVER_PASSWORD}
            content = replaceVariables(content, params);

            // 获取 collection
            Object collection = params.get(collectionName);
            if (collection instanceof Iterable) {
                StringBuilder foreachContent = new StringBuilder(open);
                int i = 0;
                for (Object item : (Iterable<?>) collection) {
                    String itemValue = item.toString();

                    // 替换 #{item} 为 itemValue
                    String itemContent = content.replace("#{" + itemName + "}", itemValue);

                    // 递归解析 <if> 标签，确保每个项目都经过条件判断
                    HashMap<String, Object> paramMapTemp = new HashMap<>(params);
                    paramMapTemp.put(itemName, itemValue);
                    itemContent = applyIfCondition(itemContent, paramMapTemp);

                    // 只有当条件满足时才保留内容
                    if (!itemContent.isEmpty()) {
                        foreachContent.append(itemContent);

                        // 添加 separator
                        if (i < ((Iterable<?>) collection).spliterator().getExactSizeIfKnown() - 1) {
                            foreachContent.append(separator);
                        }
                        i++;
                    }
                }
                foreachContent.append(close);
                matcher.appendReplacement(resultText, beforeReplace(foreachContent.toString()));
            } else {
                matcher.appendReplacement(resultText, "");
            }
        }
        matcher.appendTail(resultText);
        return resultText.toString();
    }

    // 替换模板中的变量并解析
    public static String parseTextTemplate(String textTemplate, Map<String, Object> params) {
        // 处理 <foreach> 和 <if> 标签
//        .replaceAll("\\s{2,}", "  ")
        String resultText = applyForEach(textTemplate, params);
        resultText = applyIfCondition(resultText, params);
        return resultText;
    }

    private static Object resolve(String expression, Map<String, Object> params) {
        String[] parts = expression.split("\\.");  // 以 . 分隔路径

        Object value = params.get(parts[0]);  // 获取第一个字段
        for (int i = 1; i < parts.length; i++) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(parts[i]);  // 如果是 Map，则根据键查找值
            } else if (value != null) {
                // 对象字段查找，反射方式获取字段值
                try {
                    value = value.getClass().getField(parts[i]).get(value);  // 通过反射获取字段
                } catch (Exception e) {
                    return null;  // 如果字段不存在，返回 null
                }
            }
            if (value == null) {
                return null;
            }
        }
        return value;
    }
}
