import cn.hutool.json.JSONUtil;
import com.pro.snowball.common.util.FreemarkerMethodGetGitName;
import com.pro.snowball.common.util.FreemarkerMethodJson;
import com.pro.snowball.common.util.TemplateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TemplateUtilTest {


    public static void main(String[] args) {
        // 示例模板内容
        String templateContent = """
                <#list servers as server>
                  <#list modules as module>
                    <#if module.code != "libs">
                      # 结束旧进程
                      pid=$(ps -ef | grep '${platform.deployRoot}/${platform.code}-${module.code}.jar' | grep -v grep | awk '{print $2}')
                      if [ -n "$pid" ]; then
                      kill -9 $pid
                      echo "Previous process stopped."
                      fi
                      s
                      # 备份旧的 JAR 文件和日志文件
                      BACKUP_DIR="${platform.deployRoot}/backup/$(date +%Y%m%d%H%M%S)"
                      mkdir -p "$BACKUP_DIR"
                      cp ${platform.deployRoot}/${platform.code}-${module.code}.jar "$BACKUP_DIR/${platform.code}-${module.code}.jar"
                      cp ${platform.deployRoot}/${module.code}.log "$BACKUP_DIR/${module.code}.log"
                      echo "Backup completed to $BACKUP_DIR."
                      s
                      # 启动新进程
                      export LC_ALL=en_US.UTF-8
                      cd ${platform.deployRoot}/
                      nohup java ${module.javaEnv} -Dloader.path=${platform.deployRoot}/lib -jar -Dfile.encoding=UTF-8 -Dspring.profiles.active=prod,platform ${platform.deployRoot}/${platform.code}-${module.code}.jar > ${platform.deployRoot}/${module.code}.log 2>&1 & disown
                      exit 0
                      s
                      # 检查启动成功
                      tail -f ${platform.deployRoot}/${module.code}.log | while read line; do
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
        List<String> variables = TemplateUtil.extractRootVariables(templateContent);

        // 输出结果
        System.out.println("Root Variables: " + variables);
    }
}
