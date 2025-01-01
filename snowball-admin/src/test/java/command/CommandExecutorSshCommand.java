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
public class CommandExecutorSshCommand implements ICommandExecutor {

    /**
     * 访问远程服务器
     *
     * @param remoteServer 服务器信息
     * @param command      ssh命令
     * @param params       暂时无用
     * @return 访问结果
     */
    @Override
    public BaseCommandResult execute(RemoteServer remoteServer, String command, Map<String, Object> params) {
        boolean success = false;
        String exceptionMessage = null;
        String output = null;
        String error = null;

        String privateKeyPath = remoteServer.getPrivateKeyLocalPath();
        String host = remoteServer.getHost();
        String username = remoteServer.getUsername();
        try {
            // 构建 SSH 命令，使用 bash 执行命令
            CommandLine cmdLine = new CommandLine("/usr/bin/ssh");
            cmdLine.addArgument("-p"); // 指定端口选项
            cmdLine.addArgument(remoteServer.getPort().toString()); // 端口号
            cmdLine.addArgument("-i");
            cmdLine.addArgument(privateKeyPath);
            cmdLine.addArgument(String.format("%s@%s", username, host));
            cmdLine.addArgument(command, false);        // 传递命令给 bash

            DefaultExecutor executor = new DefaultExecutor();

            // 捕获输出和错误流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            executor.setStreamHandler(streamHandler);

            System.out.println("Executing: " + cmdLine);
            int exitCode = executor.execute(cmdLine);

            // 获取标准输出和错误输出
            output = outputStream.toString(StandardCharsets.UTF_8);
            error = errorStream.toString(StandardCharsets.UTF_8);
            // 如果命令退出码不是 0 或有错误输出，则输出错误信息
            if (exitCode == 0) {
                success = true;
            }
        } catch (Exception e) {
            exceptionMessage = e.getMessage();
        }
        return new BaseCommandResult(success, output, error, exceptionMessage);
    }
}
