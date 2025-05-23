package com.pro.snowball.common.service.cmd.sub;

import com.pro.snowball.common.service.cmd.LoggerExtendService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.LogOutputStream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CmdRemoteLogger extends LogOutputStream {

    private final BufferedWriter writer;
    private final String orderKey;
    private final LoggerExtendService loggerService;

    public CmdRemoteLogger(String filePath, boolean append, LoggerExtendService loggerService, String orderKey) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, StandardCharsets.UTF_8, append);
        this.orderKey = orderKey;
        this.writer = new BufferedWriter(fileWriter);
        this.loggerService = loggerService;
    }

    @Override
    @SneakyThrows
    public void processLine(String line, int level) {
        writer.write(line);
        // 统一的输出逻辑
        loggerService.receiveLine(orderKey, line);
        writer.newLine();
        writer.flush();
    }
}