package command;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;
import java.io.InputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RemoteCommandExecutor {
    public static void main(String[] args) {
        String privateKeyPath = "/Users/zubin/.ssh/id_rsa_github3"; // 私钥路径
        String serverIp = "111.230.10.171"; // 服务器IP
        String user = "root"; // SSH用户名
        String command = "cd /project/snowball && nohup java -Xms128m -Xmx1024m -Dloader.path=/project/snowball/lib -Dspring.profiles.active=prod,platform -jar /project/snowball/snowball-user.jar > /project/snowball/nohup.out 2>&1 &";  // 执行的启动命令
        String logFilePath = "/project/snowball/user.log"; // 远程日志文件路径

        try {
            // 创建JSch对象
            JSch jsch = new JSch();
            jsch.setLogger(new com.jcraft.jsch.Logger() {
                public boolean isEnabled(int level) {
                    return true;
                }

                public void log(int level, String message) {
                    System.out.println("level " + level + ": " + message);
                }
            });

            // 添加私钥文件
            jsch.addIdentity(privateKeyPath, (String) "");  // 不传递密码

            // 创建SSH会话
            Session session = jsch.getSession(user, serverIp, 22);
            session.setConfig("StrictHostKeyChecking", "no"); // 关闭主机密钥检查，防止第一次连接时提示确认

            // 打开连接
            session.connect();

            // 创建exec通道并设置启动命令
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            // 获取执行命令后的输出流
            InputStream inputStream = channel.getInputStream();

            // 执行命令
            channel.connect();

            // 读取输出结果并同时输出到控制台
            int readByte;
            while ((readByte = inputStream.read()) != -1) {
                System.out.print((char) readByte);  // 输出到控制台
            }

            // 断开连接
            channel.disconnect();

            // 等待一定时间确保应用已启动（可以根据实际情况调整时间）
            Thread.sleep(5000);  // 等待 5 秒钟

            // 获取远程日志文件内容
            String remoteLogCommand = "tail -n 100 " + logFilePath; // 读取最近100行日志
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(remoteLogCommand);

            // 获取远程日志输出流
            inputStream = channel.getInputStream();
            BufferedWriter writer = new BufferedWriter(new FileWriter("local_user.log", true));  // 本地保存日志文件

            // 执行读取日志命令
            channel.connect();

            // 读取远程日志并输出到本地文件和控制台
            while ((readByte = inputStream.read()) != -1) {
                char outputChar = (char) readByte;
                System.out.print(outputChar);  // 输出到控制台
                writer.write(outputChar);     // 输出到本地文件
            }

            // 断开连接
            channel.disconnect();

            // 关闭文件写入流
            writer.close();

            // 检查日志文件中是否包含 Spring Boot 启动成功的标志
            if (checkIfSpringBootStarted("local_user.log")) {
                System.out.println("Spring Boot 启动成功，进入下一步...");
                // 在此处加入下一步操作的代码
            } else {
                System.out.println("Spring Boot 启动失败，请检查日志。");
            }

            // 断开SSH会话
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 检查日志文件中是否包含 Spring Boot 启动成功的标志
    private static boolean checkIfSpringBootStarted(String logFilePath) {
        // 这里可以根据日志内容匹配一些 Spring Boot 启动成功的关键字
        // 比如：检查是否包含类似 "Started Application in ..." 这样的日志
        try {
            // 读取日志文件内容
            List<String> lines = Files.readAllLines(Paths.get(logFilePath));
            for (String line : lines) {
                if (line.contains("Started Application")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
