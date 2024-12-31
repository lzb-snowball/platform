package com.pro.snowball.common.service.cmd;

import com.pro.snowball.api.model.db.RemoteServer;
import com.pro.snowball.common.service.cmd.sub.CmdRemoteLogger;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CmdRemoteServiceImpl implements ICmdRemoteService {
    @Autowired
    private LoggerExtendService loggerService;

    @Override
    public boolean execute(RemoteServer remoteServer, List<String> commands, String infoLogFile, String errorLogFile, String logKey) {
        for (String command : commands) {
            boolean success = executeCommand(remoteServer, command, infoLogFile, errorLogFile, logKey);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    private boolean executeCommand(RemoteServer remoteServer, String command, String infoLogFile, String errorLogFile, String logKey) {
        DefaultExecutor executor = new DefaultExecutor();
        @Cleanup CmdRemoteLogger infoStream = new CmdRemoteLogger(infoLogFile, true, loggerService, logKey);
        @Cleanup CmdRemoteLogger errorStream = new CmdRemoteLogger(errorLogFile, true, loggerService, logKey);
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
}

