import com.pro.snowball.common.service.cmd.CmdLocalServiceImpl;
import com.pro.snowball.common.service.cmd.LoggerExtendService;
import lombok.SneakyThrows;

import java.util.Collections;

public class CmdLocalTestWindows {
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
        String command = "cd C:\\Users\\Public\\file\\snowball_workspace\\22\\;echo 'cd next'";
        String infoLogFile = "C:\\Users\\Public\\file\\build-info.log";
        String errorLogFile = "C:\\Users\\Public\\file\\build-error.log";
        String orderKey = "build-ui-admin";

        // 执行命令
        boolean success = cmdService.execute(Collections.singletonList(command), infoLogFile, errorLogFile, orderKey);

        // 打印执行结果
        System.out.println("Build result: " + (success ? "Success" : "Failed"));

        Thread.sleep(1000000);
    }
}
