package com.pro.snowball.common.service.cmd.sub;

import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LogOutputStream extends OutputStream {
    private final Logger logger;
    private final ByteArrayOutputStream buffer;

    public LogOutputStream(Logger logger) {
        this.logger = logger;
        this.buffer = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        buffer.write(b); // 保留字节到缓冲区

        // 每当遇到换行符时，将缓冲区内容解码输出
        if (b == '\n') {
            byte[] byteArray = buffer.toByteArray();

            // 使用 UTF-8 解码字节流
            String message = new String(byteArray, StandardCharsets.UTF_8).trim();

            // 输出到日志
            logger.info(message);

            // 清空缓冲区
            buffer.reset();
        }
    }
}
