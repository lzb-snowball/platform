package com.pro.snowball.common.service.cmd.sub;

import com.pro.snowball.common.service.cmd.LoggerExtendService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CmdLocalLogger implements Runnable {
    private final String logKey;
    private final LoggerExtendService loggerService;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    @SneakyThrows
    public CmdLocalLogger(InputStream inputStream, String filePath, Boolean append, LoggerExtendService loggerService, String logKey) {
        this.logKey = logKey;
        this.loggerService = loggerService;
        reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath, append), StandardCharsets.UTF_8));
    }

    @Override
    @SneakyThrows
    public void run() {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Building snowball-admin 2.0.0")) {
                log.warn("===line 1{}", line);
            }
            writeLine(line);
        }
    }

    @SneakyThrows
    public void writeLine(String line) {
        writer.write(line);
        loggerService.receiveLine(logKey, line);
        writer.newLine();
        writer.flush();
    }
}