package com.pro.snowball.common.service.cmd;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class CmdLocalServiceImpl implements ICmdLocalService {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    @SneakyThrows
    public boolean execute(List<String> commands, String infoLogFile, String errorLogFile) {
//        try {
//            // 准备日志文件
//            prepareLogFile(infoLogFile);
//            prepareLogFile(errorLogFile);

        // 遍历并执行命令
        for (String command : commands) {
//            log.info("Executing command: {}", command);

            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            Process process = processBuilder.start();

            // 异步处理输出流和错误流
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            executorService.execute(new StreamGobbler(inputStream, infoLogFile));
            executorService.execute(new StreamGobbler(errorStream, errorLogFile));

            // 等待命令完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return false;
//                log.error("Command failed with exit code: {}", exitCode);
//                throw new RuntimeException("Command execution failed for: " + command);
            } else {
//                log.info("Command completed successfully: {}", command);
            }
        }
//        } catch (Exception e) {
//            log.error("Error executing commands", e);
//        }
        return true;
    }

    // 准备日志文件
    private void prepareLogFile(String logFile) throws IOException {
        Files.createDirectories(Paths.get(logFile)
                .getParent());
        Files.deleteIfExists(Paths.get(logFile));
        Files.createFile(Paths.get(logFile));
    }

    // 内部类：处理流并写入日志文件
    private static class StreamGobbler implements Runnable {
        private final InputStream inputStream;
        private final String logFile;

        public StreamGobbler(InputStream inputStream, String logFile) {
            this.inputStream = inputStream;
            this.logFile = logFile;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                    writer.flush();

                    // 模拟延迟推送 WebSocket 消息
                    simulateWebSocketPush(line);
                }
            } catch (IOException e) {
                log.error("Error while processing stream for log file: {}", logFile, e);
            }
        }

        private void simulateWebSocketPush(String message) {
            // 模拟通过 WebSocket 推送消息
            log.info("WebSocket push: {}", message);
//            try {
//                Thread.sleep(200); // 延迟推送
//            } catch (InterruptedException e) {
//                Thread.currentThread()
//                        .interrupt();
//                log.error("WebSocket push interrupted", e);
//            }
        }
    }
}
