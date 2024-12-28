import com.pro.snowball.common.util.TemplateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Configuration;

import java.io.*;
import java.util.*;

public class DynamicFreemarkerTemplateDemo {

    public static void main(String[] args) throws IOException, TemplateException {
        // 模拟数据
        Map<String, Object> server2 = new HashMap<>();
        server2.put("name", "Server1");
        server2.put("privateKeyLocalPath","privateKeyLocalPath");
        server2.put("username","privateKeyLocalPath");
        server2.put("port","privateKeyLocalPath");
        server2.put("host","privateKeyLocalPath");


        List<Map<String, Object>> servers = Arrays.asList(server2);

        Map<String, Object> data = new HashMap<>();
        data.put("platform", "snowball");
        data.put("servers", servers);
        data.put("repositories", Arrays.asList("111","22"));
        data.put("modules", Arrays.asList(
                Map.of("code", "user", "javaEnv", "-Xmx1024m")
//                Map.of("code", "module3", "javaEnv", "-Xmx1024m")
        ));

        // 动态生成模板字符串
        String templateContent = """

<#list servers as server>
    <#list modules as module>
        <#if module.code != "libs">
            scp -i ${server.privateKeyLocalPath} @/platform/${platform}-${module.code}/target/${platform}-${module.code}.jar ${server.username}@${server.host}:/project/${platform}/${platform}-${module.code}.jar
        </#if>
    </#list>
</#list>                
                """;

        System.out.println(TemplateUtil.analysis(templateContent, data));
    }


}
