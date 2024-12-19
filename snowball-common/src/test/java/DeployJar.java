import org.apache.commons.exec.*;

import java.io.IOException;

public class DeployJar {
    private static final String PRIVATE_KEY_PATH = "/Users/zubin/.ssh/id_rsa_github2";
    private static final String SERVER_IP = "111.230.10.171";
    private static final String LOCAL_JAR_PATH = "/Users/zubin/IdeaProjects/snowball/platform/snowball-admin/target/snowball-admin.jar";
    private static final String REMOTE_DIRECTORY = "/project/snowball";

    public static void main(String[] args) {
        try {
            // 使用 SCP 将 JAR 文件传输到远程服务器
            String scpCommand = String.format("scp -i %s %s %s@%s:%s", PRIVATE_KEY_PATH, LOCAL_JAR_PATH, "root", SERVER_IP, REMOTE_DIRECTORY);
            executeCommand(scpCommand);

//            // 连接到远程服务器并执行 JAR 文件
//            String sshCommand = String.format("ssh -i %s root@%s 'java -jar %s/snowball-admin.jar'", PRIVATE_KEY_PATH, SERVER_IP, REMOTE_DIRECTORY);
//            executeCommand(sshCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String command) throws IOException {
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(commandLine);
    }
}
