package command;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class DebugSSHCommandApache {

    private static final String PRIVATE_KEY_PATH = "/Users/zubin/.ssh/id_rsa_github2";
    private static final String SERVER_IP = "111.230.10.171";

    private static final LogOutputStream logOutputStreamInfo = new LogOutputStream(log);
    private static final LogOutputStream logOutputStreamError = new LogOutputStream(log);

    private static final List<String> commands = List.of(
            """
                pid=$(ps -ef | grep '/project/snowball/snowball-user.jar' | grep -v grep | awk '{print $2}')
                if [ -n "$pid" ]; then
                    kill -9 $pid
                    echo "Previous process stopped."
                fi
            """,
            """
                export LC_ALL=en_US.UTF-8
                cd /project/snowball/
                nohup java -Xms128m -Xmx1024m -Dloader.path=/project/snowball/lib -jar -Dfile.encoding=UTF-8 -Dspring.profiles.active=prod,platform /project/snowball/snowball-user.jar > /project/snowball/user.log 2>&1 & disown
                exit 0
            """,
            """
                tail -f /project/snowball/user.log | while read line; do
                    echo "$line"
                    if [[ "$line" == *"Started"* ]]; then
                        echo "Application started successfully!"
                        break
                    fi
                done
            """
    );

    public static void main(String[] args) {
        commands.forEach(command -> executeCommand(PRIVATE_KEY_PATH, SERVER_IP, command));
    }

    private static boolean executeCommand(String privateKeyPath, String serverIp, String command) {
        try {
            // 构建 SSH 命令，使用 bash 执行命令
            CommandLine cmdLine = buildCommandLine(privateKeyPath, serverIp, command);

            DefaultExecutor executor = new DefaultExecutor();
            PumpStreamHandler streamHandler = new PumpStreamHandler(logOutputStreamInfo, logOutputStreamError);
            executor.setStreamHandler(streamHandler);

            log.info("Executing: {}", command);
            int exitCode = executor.execute(cmdLine);
            log.info("Execution completed");

            if (exitCode != 0) {
                throw new RuntimeException("Command execution failed with exit code " + exitCode);
            }

            return exitCode == 0;
        } catch (Exception e) {
            log.error("Error executing command", e);
            return false;
        }
    }

    private static CommandLine buildCommandLine(String privateKeyPath, String serverIp, String command) {
        // 构建 SSH 命令行
        CommandLine cmdLine = new CommandLine("/usr/bin/ssh");
        cmdLine.addArgument("-i");
        cmdLine.addArgument(privateKeyPath);
        cmdLine.addArgument("root@" + serverIp);
        cmdLine.addArgument(command, false);  // 传递命令给 bash
        return cmdLine;
    }
}
