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
    private int lineCount = 0;
    private static final int FLUSH_THRESHOLD = 20; // 每 20 行 flush 一次

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
        writer.newLine();
        loggerService.receiveLine(orderKey, line);
        lineCount++;
        if (lineCount >= FLUSH_THRESHOLD) {
            writer.flush();
            lineCount = 0;
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        writer.flush();
        writer.close();
    }
}
