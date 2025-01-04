package com.pro.snowball.common.service.cmd;

import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.json.JSONUtil;
import com.pro.snowball.api.model.vo.RemoteServer;
import com.pro.snowball.common.service.cmd.sub.CmdRemoteLogger;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class CmdRemoteServiceImpl implements ICmdRemoteService {
    @Autowired
    private LoggerExtendService loggerService;
    private static final Map<String, Process> processMap = new WeakConcurrentMap<>();

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
        try {
            CommandLine cmdLine = buildCommandLine(remoteServer, command);
            int exitCode = executor.execute(cmdLine);
            return exitCode == 0;
        } catch (Exception e) {
            log.error("Error executing command on {}", remoteServer.getHost(), e);
            return false;
        }
    }

    private CommandLine buildCommandLine(RemoteServer remoteServer, String command) {
        CommandLine cmdLine = new CommandLine("/usr/bin/ssh");
        cmdLine.addArgument("-i");
        cmdLine.addArgument(remoteServer.getPrivateKeyLocalPath());
        cmdLine.addArgument(remoteServer.getUsername() + "@" + remoteServer.getHost());
        cmdLine.addArgument(command, false); // Pass command to bash without escaping
        return cmdLine;
    }

    @Override
    public void destroy(String orderKey) {
        Process process = processMap.get(orderKey);
        if (process != null) {
            process.destroy();
        }
    }
}

