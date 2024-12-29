package com.pro.snowball.common.service.cmd.sub;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.LogOutputStream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@Slf4j
public class DelayedFileOutputStream extends LogOutputStream {

    private final BufferedWriter writer;
    private final long delayMillis;
    private final ScheduledExecutorService scheduler;

    public DelayedFileOutputStream(String filePath, boolean append, long delayMillis) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath, append));
        this.delayMillis = delayMillis;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    @SneakyThrows
    protected void processLine(String line, int level) {
//        scheduler.schedule(() -> {
//            try {
        writer.write(line);
        // 输出到日志
        log.info(line);
        writer.newLine();
        writer.flush();
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to write to log file", e);
//            }
//        }, delayMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() throws IOException {
        try {
            scheduler.shutdown();
            scheduler.awaitTermination(delayMillis * 2, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            writer.close();
        }
    }
}