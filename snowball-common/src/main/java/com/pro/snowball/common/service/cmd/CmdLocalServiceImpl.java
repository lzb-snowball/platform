package com.pro.snowball.common.service.cmd;

import cn.hutool.core.map.WeakConcurrentMap;
import com.pro.snowball.common.service.cmd.sub.CmdLocalLogger;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Data
public class CmdLocalServiceImpl implements ICmdLocalService {
    @Autowired
    private LoggerExtendService loggerService;
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    public static final Map<String, List<Process>> processMap = new WeakConcurrentMap<>();

    @Override
    @SneakyThrows
    public boolean execute(List<String> commands, String infoLogFile, String errorLogFile, String orderKey) {
        for (String command : commands) {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.environment().put("PATH", System.getenv("PATH"));
            Process process = processBuilder.start();

            // ✅ 修复：确保 processMap 正确添加
            processMap.computeIfAbsent(orderKey, k -> new ArrayList<>()).add(process);

            // ✅ 正确分开日志文件
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            CmdLocalLogger loggerInfo = new CmdLocalLogger(inputStream, infoLogFile, true, loggerService, orderKey);
            CmdLocalLogger loggerError = new CmdLocalLogger(errorStream, errorLogFile, true, loggerService, orderKey);
            loggerInfo.writeLine(command);

            executorService.execute(loggerInfo);
            executorService.execute(loggerError);

            // ✅ 等待进程结束，加超时限制
            boolean finished = process.waitFor(15, TimeUnit.MINUTES);
            if (!finished) {
                log.warn("进程超时未结束，强制终止: {}", command);
                process.descendants().forEach(p -> {
                    try {
                        p.destroyForcibly();
                    } catch (Exception e) {
                        log.warn("强制销毁子进程失败", e);
                    }
                });
                process.destroyForcibly();
                return false;
            }

            // ✅ 检查退出码
            int exitCode = process.exitValue();
            if (exitCode != 0) {
                log.warn("命令执行失败: {}, exitCode={}", command, exitCode);
                return false;
            }

            // ✅ 主动销毁已完成进程（可选）
            process.destroy();
        }
        return true;
    }


    @Override
    public void destroy(String orderKey) {
        List<Process> processes = processMap.get(orderKey);
        if (processes != null) {
            for (Process process : processes) {
                try {
                    process.descendants().forEach(descendant -> {
                        try {
                            descendant.destroyForcibly();
                        } catch (Exception e) {
                            log.warn("无法销毁子进程", e);
                        }
                    });
                    process.destroyForcibly();
                } catch (Exception e) {
                    log.warn("无法销毁主进程", e);
                }
            }
            processMap.remove(orderKey);
        }
    }
}
