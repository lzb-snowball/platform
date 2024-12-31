package com.pro.snowball.common.service.cmd.sub;

import com.pro.snowball.common.service.cmd.LoggerExtendService;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CmdLocalLogger implements Runnable {
    private final InputStream inputStream;
    private final String filePath;
    private final String logKey;
    private final Boolean append;
    private final LoggerExtendService loggerService;

    public CmdLocalLogger(InputStream inputStream, String filePath, Boolean append, LoggerExtendService loggerService, String logKey) {
        this.inputStream = inputStream;
        this.filePath = filePath;
        this.logKey = logKey;
        this.append = append;
        this.loggerService = loggerService;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8, append))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writeLine(writer, line);
            }
        } catch (IOException e) {
            log.error("Error while processing stream for log file: {}", filePath, e);
        }
    }

    public void writeLine(BufferedWriter writer, String line) throws IOException {
        writer.write(line);
        loggerService.receiveLine(logKey, line);
        writer.newLine();
        writer.flush();
    }
}