package com.pro.snowball.common.service.cmd;

import com.pro.snowball.api.model.vo.RemoteServer;
import com.pro.snowball.common.service.cmd.sub.CmdRemoteLogger;
import lombok.Cleanup;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@Setter
public class CmdRemoteServiceImpl implements ICmdRemoteService {
    @Autowired
    private LoggerExtendService loggerService;
//    @Autowired
//    private ThreadService threadService;
//    public static final Map<String, Process> processMap = new WeakConcurrentMap<>();

    @Override
    public boolean execute(RemoteServer remoteServer, List<String> commands, String infoLogFile, String errorLogFile, String orderKey) {
        for (String command : commands) {
            boolean success = executeCommand(remoteServer, command, infoLogFile, errorLogFile, orderKey);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    private boolean executeCommand(RemoteServer remoteServer, String command, String infoLogFile, String errorLogFile, String orderKey) {
        DefaultExecutor executor = new DefaultExecutor();
        @Cleanup CmdRemoteLogger infoStream = new CmdRemoteLogger(infoLogFile, true, loggerService, orderKey);
        @Cleanup CmdRemoteLogger errorStream = new CmdRemoteLogger(errorLogFile, true, loggerService, orderKey);
        // 输出 当前指令
        infoStream.processLine(command, 0);

        PumpStreamHandler streamHandler = new PumpStreamHandler(infoStream, errorStream);
        executor.setStreamHandler(streamHandler);
        executor.setWatchdog(new ExecuteWatchdog.Builder().setTimeout(Duration.ofMinutes(5)).get());  // 5分钟超时
        CommandLine cmdLine = buildCommandLine(remoteServer, command);
        try {
            int exitCode = executor.execute(cmdLine);
            return exitCode == 0;
        } catch (IOException e) {
            log.error("远程命令执行失败: {}", command, e);
            return false;
        }
    }

    /**
     *
     * @param remoteServer
     * @param command
     * @return
     * 常见异常
     *  1.如果读不到 java.则是 需要在 source /etc/environment 这里配置 JAVA_HOME 和 PATH
     *  2.秘钥权文件的权限太公开 chmod 600 /root/.ssh/id_rsa_baoyi_prod
     */
    private CommandLine buildCommandLine(RemoteServer remoteServer, String command) {
        CommandLine cmdLine = new CommandLine("/usr/bin/ssh");
        cmdLine.addArgument("-i");
        cmdLine.addArgument(remoteServer.getPrivateKeyLocalPath());
        cmdLine.addArgument(remoteServer.getUsername() + "@" + remoteServer.getHost());

//        String wrapped = String.format("bash -l -c \"%s\"", command.replace("\"", "\\\""));
//        cmdLine.addArgument(wrapped, false);
        cmdLine.addArgument(command, false); // Pass command to bash without escaping
//        cmdLine.addArgument("bash -l -c '" + command + "'", false);
        return cmdLine;
    }

    @Override
    public void destroy(String orderKey) {

    }
}

