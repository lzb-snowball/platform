package com.pro.snowball.common.service.cmd;

import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.json.JSONUtil;
import com.pro.snowball.common.service.cmd.sub.CmdLocalLogger;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@Data
public class CmdLocalServiceImpl implements ICmdLocalService {
    @Autowired
    private LoggerExtendService loggerService;
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Map<String, Process> processMap = new WeakConcurrentMap<>();

    @Override
    @SneakyThrows
    public boolean execute(List<String> commands, String infoLogFile, String errorLogFile, String orderKey) {
        // 遍历并执行命令
        for (String command : commands) {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.environment().put("PATH", System.getenv("PATH"));
            Process process = processBuilder.start();
            processMap.put(orderKey, process);
            // 异步处理输出流和错误流
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            CmdLocalLogger loggerInfo = new CmdLocalLogger(inputStream, infoLogFile, true, loggerService, orderKey);
            CmdLocalLogger loggerError = new CmdLocalLogger(errorStream, infoLogFile, true, loggerService, orderKey);
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

    @Override
    public void destroy(String orderKey) {
        Process process = processMap.get(orderKey);
        if (process != null) {
            processMap.remove(orderKey);
            process.destroyForcibly();
        }
    }
}
