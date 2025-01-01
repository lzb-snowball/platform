package command;

import com.pro.snowball.api.model.vo.RemoteServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CommandExecutorSshMavenInstall implements ICommandExecutor {

    /**
     * 执行 Maven 安装命令
     *
     * @param remoteServer 服务器信息（此处可忽略）
     * @param command      Maven 命令（例如：`mvn clean install`）
     * @param params       暂时无用
     * @return 执行结果
     */
    @Override
    public BaseCommandResult execute(RemoteServer remoteServer, String command, Map<String, Object> params) {
        boolean success = false;
        String exceptionMessage = null;
        String output = null;
        String error = null;

        try {
            // 构建 Maven 命令
            CommandLine cmdLine = CommandLine.parse(command);

            DefaultExecutor executor = new DefaultExecutor();

            // 捕获输出和错误流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            executor.setStreamHandler(streamHandler);

            System.out.println("Executing: " + cmdLine);
            int exitCode = executor.execute(cmdLine);
            System.out.println("Command executed with exit code: " + exitCode);

            // 获取标准输出和错误输出
            output = outputStream.toString(StandardCharsets.UTF_8);
            error = errorStream.toString(StandardCharsets.UTF_8);

            if (output.isEmpty()) {
                System.out.println("No output for command: " + command);
            } else {
                System.out.println("Command Output:\n" + output);
            }

            // 判断执行结果
            if (exitCode == 0) {
                success = true;
            }
        } catch (Exception e) {
            log.error("Error executing command: " + e.getMessage(), e);
            exceptionMessage = e.getMessage();
        }

        return new BaseCommandResult(success, output, error, exceptionMessage);
    }
}
