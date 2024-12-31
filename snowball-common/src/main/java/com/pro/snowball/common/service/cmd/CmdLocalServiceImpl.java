package com.pro.snowball.common.service.cmd;

import com.pro.snowball.common.service.cmd.sub.CmdLocalLogger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class CmdLocalServiceImpl implements ICmdLocalService {
    @Autowired
    private LoggerExtendService loggerService;
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    @SneakyThrows
    public boolean execute(List<String> commands, String infoLogFile, String errorLogFile, String logKey) {
        // 遍历并执行命令
        for (String command : commands) {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            Process process = processBuilder.start();

            // 异步处理输出流和错误流
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            CmdLocalLogger loggerInfo = new CmdLocalLogger(inputStream, infoLogFile, true, loggerService, logKey);
            CmdLocalLogger loggerError = new CmdLocalLogger(errorStream, errorLogFile, true, loggerService, logKey);
            // 输出 当前指令
            loggerInfo.writeLine(command);

            executorService.execute(loggerInfo);
            executorService.execute(loggerError);

            // 等待命令完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return false;
            }
        }
        return true;
    }
}
