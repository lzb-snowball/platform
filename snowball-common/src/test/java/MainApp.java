import com.pro.common.modules.service.dependencies.modelauth.base.MessageService;
import com.pro.snowball.common.service.cmd.CmdLocalServiceImpl;
import com.pro.snowball.common.service.cmd.LoggerExtendService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Collections;

public class MainApp {
    @SneakyThrows
    public static void main(String[] args) {
        // 创建 Spring 上下文

        // 获取 CmdLocalServiceImpl Bean
        CmdLocalServiceImpl cmdService = new CmdLocalServiceImpl();
        cmdService.setLoggerService(new LoggerExtendService(null){
            @Override
            public void receiveLine(String orderKey, String line) {
                System.out.println(line);
//                super.receiveLine(orderKey, line);
            }
        });
        // 设定命令
        String command = "cd /Users/zubin/IdeaProjects/snowball/ui-admin && npm run 'build prod'";
        String infoLogFile = "/Users/zubin/IdeaProjects/snowball/ui-admin/build-info.log";
        String errorLogFile = "/Users/zubin/IdeaProjects/snowball/ui-admin/build-error.log";
        String orderKey = "build-ui-admin";

        // 执行命令
        boolean success = cmdService.execute(Collections.singletonList(command), infoLogFile, errorLogFile, orderKey);

        // 打印执行结果
        System.out.println("Build result: " + (success ? "Success" : "Failed"));

        Thread.sleep(1000000);
    }
}
