import com.pro.snowball.api.model.vo.RemoteServer;
import com.pro.snowball.common.service.cmd.CmdLocalServiceImpl;
import com.pro.snowball.common.service.cmd.CmdRemoteServiceImpl;
import com.pro.snowball.common.service.cmd.LoggerExtendService;
import lombok.SneakyThrows;

import java.util.Collections;

public class CmdRemoteTestMac {
    @SneakyThrows
    public static void main(String[] args) {
        // 创建 Spring 上下文

        // 获取 CmdLocalServiceImpl Bean
        CmdRemoteServiceImpl cmdService = new CmdRemoteServiceImpl();
        cmdService.setLoggerService(new LoggerExtendService(null){
            @Override
            public void receiveLine(String orderKey, String line) {
                System.out.println(line);
//                super.receiveLine(orderKey, line);
            }
        });
        // 设定命令
        String command = "java --version";
        String infoLogFile = "/Users/zubin/IdeaProjects/snowball/logs/access_log/build-info.log";
        String errorLogFile = "/Users/zubin/IdeaProjects/snowball/logs/access_log/build-error.log";
        String orderKey = "build-ui-admin";

        RemoteServer remoteServer = new RemoteServer();
        remoteServer.setUsername("root");
        remoteServer.setHost("113.45.237.207");
        remoteServer.setPort(22);
        remoteServer.setPrivateKeyLocalPath("/Users/zubin/IdeaProjects/snowball/platform/snowball-common/src/test/resources/id_rsa_baoyi_prod");
        // 执行命令
        boolean success = cmdService.execute(remoteServer, Collections.singletonList(command), infoLogFile, errorLogFile, orderKey);

        // 打印执行结果
        System.out.println("Build result: " + (success ? "Success" : "Failed"));

        Thread.sleep(1000000);
    }
}
