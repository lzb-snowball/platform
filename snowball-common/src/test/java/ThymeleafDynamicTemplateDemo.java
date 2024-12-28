//import org.thymeleaf.context.Context;
//import org.thymeleaf.TemplateEngine;
//
//import java.util.*;
//
//public class ThymeleafDynamicTemplateDemo {
//    public static void main(String[] args) {
//        // 模拟数据
//        List<Map<String, Object>> servers = new ArrayList<>();
//
//        Map<String, Object> server1 = new HashMap<>();
//        server1.put("name", "Server1");
//        List<Map<String, Object>> modules1 = new ArrayList<>();
//        modules1.add(Map.of("code", "module1"));
//        modules1.add(Map.of("code", "libs"));
//        server1.put("modules", modules1);
//
//        Map<String, Object> server2 = new HashMap<>();
//        server2.put("name", "Server2");
//        List<Map<String, Object>> modules2 = new ArrayList<>();
//        modules2.add(Map.of("code", "module2"));
//        modules2.add(Map.of("code", "module3"));
//        server2.put("modules", modules2);
//
//        servers.add(server1);
//        servers.add(server2);
//
//        // 设置模板上下文
//        Context context = new Context();
//        context.setVariable("servers", servers);
//        context.setVariable("platform", "my-platform");
//
//        // 定义动态模板
//        String template = "<ul>" +
//                "<li th:each=\"server : ${servers}\">" +
//                "<span th:text=\"${server.name}\">Server Name</span>" +
//                "<ul>" +
//                "<li th:each=\"module : ${server.modules}\" th:if=\"${module.code != 'libs'}\">" +
//                "<span th:text=\"${server.name} + ' ' + ${module.code}\">Module Info</span>" +
//                "</li>" +
//                "</ul>" +
//                "</li>" +
//                "</ul>";
//
//        // 创建 Thymeleaf 模板引擎
//        TemplateEngine templateEngine = new TemplateEngine();
//
//        // 渲染模板
//        String result = templateEngine.process(template, context);
//
//        // 输出结果
//        System.out.println(result);
//    }
//}
