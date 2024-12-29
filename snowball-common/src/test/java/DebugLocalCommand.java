import com.pro.snowball.common.service.cmd.sub.LogOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

@Slf4j
public class DebugLocalCommand {

    private static final String WORKSPACE = "/Users/zubin/snowball-workspace";

    private static final LogOutputStream logOutputStreamInfo = new LogOutputStream(log); // 输出流日志
    private static final LogOutputStream logOutputStreamError = new LogOutputStream(log); // 错误流日志

    @Test
    public void testExecuteLocalCommands() {
        // 按顺序列出需要执行的命令
        String[] commands = {"""
                cd /Users/zubin/snowball-workspace
            """,
//            """
//                git clone git@github.com:lzb-snowball/platform.git
//            """,
                """
                pwd
            """,
//            "cd " + WORKSPACE,
//            "pwd",  // 显示当前工作目录
//            "ls -l", // 列出当前目录下的文件
//            "echo 'All commands executed successfully'"
        };

        executeLocalCommands(commands);
    }

    public void executeLocalCommands(String[] commands) {
        try {
            // 使用 ProcessBuilder 执行命令
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(WORKSPACE));  // 设置工作目录为 WORKSPACE

            for (String command : commands) {
                log.info("Executing: {}", command);

                // 执行命令
                processBuilder.command("bash", "-c", command);
                Process process = processBuilder.start();
                // 获取输出流和错误流并将其传递给 com.pro.snowball.common.service.cmd.sub.LogOutputStream
                StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), logOutputStreamInfo); // 处理标准输出
                StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), logOutputStreamError); // 处理错误输出

                // 启动线程处理输出流
                new Thread(outputGobbler).start();
                new Thread(errorGobbler).start();

                // 等待命令执行完成
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    throw new RuntimeException("Command execution failed with exit code " + exitCode);
                }

                log.info("Execution completed for: {}", command);
            }
        } catch (Exception e) {
            log.error("Error executing commands", e);
        }
    }
}
