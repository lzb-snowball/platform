import com.pro.snowball.common.service.cmd.sub.LogOutputStream;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private LogOutputStream logOutputStream;

    public StreamGobbler(InputStream inputStream, LogOutputStream logOutputStream) {
        this.inputStream = inputStream;
        this.logOutputStream = logOutputStream;
    }

    @Override
    @SneakyThrows
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logOutputStream.write((line + "\n").getBytes()); // 将输出写入日志流
            }
        } catch (IOException e) {
            logOutputStream.write(("Error reading stream: " + e.getMessage() + "\n").getBytes());
        }
    }
}
