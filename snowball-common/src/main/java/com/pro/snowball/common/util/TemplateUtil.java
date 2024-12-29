package com.pro.snowball.common.util;

import cn.hutool.json.JSONUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TemplateUtil {
    // 创建 Freemarker 配置对象
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

    static {
        try {
            cfg.setClassForTemplateLoading(TemplateUtil.class, "/");
            cfg.setDefaultEncoding("UTF-8");

            // 注册自定义方法
            cfg.setSharedVariable("json", new JsonStringMethod());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static String analysis(String templateContent, Map<String, Object> paramMap) {
//        templateContent = templateContent.replace("<@remote", "&lt;@remote").replace("</@remote>", "&lt;/@remote&gt;");

        // 使用 StringReader 创建模板
        Template template = new Template("dynamicTemplate", new StringReader(templateContent), cfg);

        // 渲染模板并输出结果
        StringWriter writer = new StringWriter();
        try {
            template.process(paramMap, writer);
        } catch (Exception e) {
            log.warn("freemarker error \n {} {}", templateContent, JSONUtil.toJsonPrettyStr(paramMap));
            throw new RuntimeException(e);
        }
        return writer.toString();
    }


    public static List<String> extractRootVariables(String templateContent) {
        // 定义正则表达式匹配 FreeMarker 根变量及其子变量
        String regex = "<#list\\s+(\\w+)\\s+as\\s+(\\w+)>|\\$\\{(\\w+)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(templateContent);

        // 使用 Set 存储去重的根变量
        Set<String> rootVariables = new LinkedHashSet<>(); // 保证顺序

        // 使用 Set 记录子变量，避免重复添加
        Set<String> excludedVariables = new HashSet<>();

        // 提取变量
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                rootVariables.add(matcher.group(1)); // 匹配 <#list ... as ...> 中的根变量
                excludedVariables.add(matcher.group(2)); // 匹配 as 后的子变量
            }
            if (matcher.group(3) != null && !excludedVariables.contains(matcher.group(3))) {
                rootVariables.add(matcher.group(3)); // 匹配 ${...}，排除子变量
            }
        }

        // 返回去重后的变量列表
        return new ArrayList<>(rootVariables);
    }

    public static void main(String[] args) {
        // 示例模板内容
        String templateContent = """
                <#list servers as server>
                  <#list modules as module>
                    <#if module.code != "libs">
                      # 结束旧进程
                      pid=$(ps -ef | grep '/project/${platform}/${platform}-${module.code}.jar' | grep -v grep | awk '{print $2}')
                      if [ -n "$pid" ]; then
                      kill -9 $pid
                      echo "Previous process stopped."
                      fi
                      s
                      # 备份旧的 JAR 文件和日志文件
                      BACKUP_DIR="/project/${platform}/backup/$(date +%Y%m%d%H%M%S)"
                      mkdir -p "$BACKUP_DIR"
                      cp /project/${platform}/${platform}-${module.code}.jar "$BACKUP_DIR/${platform}-${module.code}.jar"
                      cp /project/${platform}/${module.code}.log "$BACKUP_DIR/${module.code}.log"
                      echo "Backup completed to $BACKUP_DIR."
                      s
                      # 启动新进程
                      export LC_ALL=en_US.UTF-8
                      cd /project/${platform}/
                      nohup java ${module.javaEnv} -Dloader.path=/project/${platform}/lib -jar -Dfile.encoding=UTF-8 -Dspring.profiles.active=prod,platform /project/${platform}/${platform}-${module.code}.jar > /project/${platform}/${module.code}.log 2>&1 & disown
                      exit 0
                      s
                      # 检查启动成功
                      tail -f /project/${platform}/${module.code}.log | while read line; do
                      echo "$line"
                      if [[ "$line" == *"Started"* ]]; then
                      echo "Application started successfully!"
                      break
                      fi
                      done
                    </#if>
                  </#list>
                </#list>
                """;

        // 提取根变量
        List<String> variables = extractRootVariables(templateContent);

        // 输出结果
        System.out.println("Root Variables: " + variables);
    }
}
