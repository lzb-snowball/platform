package template;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RemoteCommandExecutor {

    public static void main(String[] args) {
        String privateKeyPath = "/Users/zubin/.ssh/id_rsa_github2"; // 私钥路径
        String serverIp = "111.230.10.171"; // 远程服务器IP

        try {
            // 直接通过一个命令来获取 PID 并结束进程
            String killCommand = "ps aux | grep '[l]ottery-user.jar' | awk '{print $2}' | xargs kill -9";
            String sshCommand = "ssh -i " + privateKeyPath + " root@" + serverIp + " \"" + killCommand + "\"";

            Process process = new ProcessBuilder("bash", "-c", sshCommand).start();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // 打印命令的输出
            }

            // 等待命令执行完毕
            process.waitFor();
            System.out.println("Process kill command executed.");
        } catch (Exception e) {
            e.printStackTrace(); // 捕获并打印异常
        }
    }
}
