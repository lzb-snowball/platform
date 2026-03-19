package com.pro.snowball.common.service.cmd;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Setter
public class CmdRemoteServiceImpl implements ICmdRemoteService {
    private static final int DEFAULT_SSH_PORT = 22;
    private static final Duration COMMAND_TIMEOUT = Duration.ofMinutes(5);
    private static final String DEFAULT_KNOWN_HOSTS_FILE = Paths.get(System.getProperty("user.home"), ".ssh", "known_hosts").toString();
    private static final String KNOWN_HOSTS_FILE = System.getProperty("snowball.ssh.known-hosts-file", DEFAULT_KNOWN_HOSTS_FILE);
    private static final String STRICT_HOST_KEY_CHECKING = System.getProperty("snowball.ssh.strict-host-key-checking", "no");

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
        @Cleanup CmdRemoteLogger infoStream = new CmdRemoteLogger(infoLogFile, true, loggerService, orderKey);
        @Cleanup CmdRemoteLogger errorStream = new CmdRemoteLogger(errorLogFile, true, loggerService, orderKey);
        // 输出 当前指令
        infoStream.processLine(command, 0);

        String privateKeyPassword = remoteServer.getPrivateKeyPassword();
        if (privateKeyPassword != null && !privateKeyPassword.isBlank()) {
            return executeCommandWithPrivateKeyPassword(remoteServer, command, infoStream, errorStream);
        }
        return executeCommandWithLocalSsh(remoteServer, command, infoStream, errorStream);
    }

    private boolean executeCommandWithLocalSsh(RemoteServer remoteServer, String command, CmdRemoteLogger infoStream, CmdRemoteLogger errorStream) {
        DefaultExecutor executor = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(infoStream, errorStream);
        executor.setStreamHandler(streamHandler);
        executor.setWatchdog(new ExecuteWatchdog.Builder().setTimeout(COMMAND_TIMEOUT).get());  // 5分钟超时
        CommandLine cmdLine = buildCommandLine(remoteServer, command);
        try {
            int exitCode = executor.execute(cmdLine);
            return exitCode == 0;
        } catch (IOException e) {
            log.error("远程命令执行失败: {}", command, e);
            return false;
        }
    }

    private boolean executeCommandWithPrivateKeyPassword(RemoteServer remoteServer, String command, CmdRemoteLogger infoStream, CmdRemoteLogger errorStream) {
        Session session = null;
        ChannelExec channel = null;
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
        try {
            JSch jsch = new JSch();
            loadKnownHosts(jsch);
            byte[] passphrase = remoteServer.getPrivateKeyPassword().getBytes(StandardCharsets.UTF_8);
            try {
                jsch.addIdentity(remoteServer.getPrivateKeyLocalPath(), passphrase);
            } finally {
                Arrays.fill(passphrase, (byte) 0);
            }
            session = jsch.getSession(remoteServer.getUsername(), remoteServer.getHost(), resolvePort(remoteServer));
            session.setConfig("StrictHostKeyChecking", STRICT_HOST_KEY_CHECKING);
            session.connect((int) COMMAND_TIMEOUT.toMillis());

            channel = (ChannelExec) session.openChannel("exec");
            channel.setInputStream(null);
            channel.setCommand(command);
            channel.setOutputStream(outBuffer);
            channel.setErrStream(errBuffer);
            channel.connect((int) COMMAND_TIMEOUT.toMillis());

            long endAt = System.currentTimeMillis() + COMMAND_TIMEOUT.toMillis();
            while (!channel.isClosed()) {
                if (System.currentTimeMillis() > endAt) {
                    log.error("远程命令执行超时: {}", command);
                    return false;
                }
                Thread.sleep(100);
            }

            processLogBuffer(outBuffer, infoStream);
            processLogBuffer(errBuffer, errorStream);
            return channel.getExitStatus() == 0;
        } catch (Exception e) {
            log.error("远程命令执行失败: {}", command, e);
            return false;
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
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
        cmdLine.addArgument("-p");
        cmdLine.addArgument(String.valueOf(resolvePort(remoteServer)));
        cmdLine.addArgument("-i");
        cmdLine.addArgument(remoteServer.getPrivateKeyLocalPath());
        cmdLine.addArgument(remoteServer.getUsername() + "@" + remoteServer.getHost());

//        String wrapped = String.format("bash -l -c \"%s\"", command.replace("\"", "\\\""));
//        cmdLine.addArgument(wrapped, false);
        cmdLine.addArgument(command, false); // Pass command to bash without escaping
//        cmdLine.addArgument("bash -l -c '" + command + "'", false);
        return cmdLine;
    }

    private int resolvePort(RemoteServer remoteServer) {
        return remoteServer.getPort() == null ? DEFAULT_SSH_PORT : remoteServer.getPort();
    }

    private void loadKnownHosts(JSch jsch) {
        Path knownHostsPath = Paths.get(KNOWN_HOSTS_FILE);
        if (!Files.exists(knownHostsPath)) {
            return;
        }
        try {
            jsch.setKnownHosts(KNOWN_HOSTS_FILE);
        } catch (Exception e) {
            log.warn("加载 known_hosts 失败: {}", KNOWN_HOSTS_FILE, e);
        }
    }

    private void processLogBuffer(ByteArrayOutputStream buffer, CmdRemoteLogger logger) throws IOException {
        String text = buffer.toString(StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.processLine(line, 0);
            }
        }
    }

    @Override
    public void destroy(String orderKey) {

    }
}
