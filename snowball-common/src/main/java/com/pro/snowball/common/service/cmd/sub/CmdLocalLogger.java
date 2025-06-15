package com.pro.snowball.common.service.cmd.sub;

import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.api.util.StrUtils;
import com.pro.snowball.common.service.cmd.LoggerExtendService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CmdLocalLogger implements Runnable {
    private int lineCount = 0;
    private static final int FLUSH_THRESHOLD = 20; // 每 20 行 flush 一次

    private final String orderKey;
    private final LoggerExtendService loggerService;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    @SneakyThrows
    public CmdLocalLogger(InputStream inputStream, String filePath, Boolean append, LoggerExtendService loggerService, String orderKey) {
        this.orderKey = orderKey;
        this.loggerService = loggerService;
        reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, append), StandardCharsets.UTF_8));
    }

    @Override
    @SneakyThrows
    public void run() {
        String line;
        while ((line = getLine()) != null) {
            writeLine(line);
        }
    }

    private String getLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return StrUtils.EMPTY;
        }
    }

    @SneakyThrows
    public void writeLine(String line) {
        writer.write(line);
        loggerService.receiveLine(orderKey, line);
        writer.newLine();
        lineCount++;
        if (lineCount >= FLUSH_THRESHOLD) {
            writer.flush();
            lineCount = 0;
        }
    }
}
