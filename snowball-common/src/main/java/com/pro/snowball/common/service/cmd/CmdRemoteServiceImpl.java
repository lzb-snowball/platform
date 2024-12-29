package com.pro.snowball.common.service.cmd;

import com.pro.snowball.api.model.db.RemoteServer;
import com.pro.snowball.common.service.cmd.sub.DelayedFileOutputStream;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class CmdRemoteServiceImpl implements ICmdRemoteService {

    @Override
    public boolean execute(RemoteServer remoteServer, List<String> commands, String infoLogFile, String errorLogFile) {
        if (remoteServer == null || commands == null || commands.isEmpty()) {
//            throw new IllegalArgumentException("Remote server and commands must not be null or empty");
        }

        for (String command : commands) {
            boolean success = executeCommand(remoteServer, command, infoLogFile, errorLogFile);
            if (!success) {
//                log.error("Failed to execute command: {}", command);
                return false;
//                throw new RuntimeException("Command execution failed for: " + command);
            }
        }
        return true;
    }

    @SneakyThrows
    private boolean executeCommand(RemoteServer remoteServer, String command, String infoLogFile, String errorLogFile) {
        DefaultExecutor executor = new DefaultExecutor();
        @Cleanup DelayedFileOutputStream infoStream = new DelayedFileOutputStream(infoLogFile, true, 3000);
        @Cleanup DelayedFileOutputStream errorStream = new DelayedFileOutputStream(errorLogFile, true, 3000);
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
//        finally {
//            try {
//                infoStream.close();
//                errorStream.close();
//            } catch (IOException e) {
//                log.error("Failed to close log files", e);
//            }
//        }
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

