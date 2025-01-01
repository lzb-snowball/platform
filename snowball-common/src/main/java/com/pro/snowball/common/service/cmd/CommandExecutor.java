package com.pro.snowball.common.service.cmd;

import java.io.*;
import java.util.*;

public class CommandExecutor {
    public static void main(String[] args) throws IOException, InterruptedException {
//        // 根目录
        File rootDir = new File("/Users/Shared/file/snowball_workspace/4/");
//
//        // 执行命令块
//        executeCommand(new String[]{"rm", "-rf", "ui-admin"}, rootDir);
//        executeCommand(new String[]{"git", "clone", "git@github.com:lzb-snowball/ui-admin.git"}, rootDir);
//
        File uiAdminDir = new File(rootDir, "ui-admin");
//        executeCommand(new String[]{"rm", "-rf", "parent-ui"}, new File(uiAdminDir, "src"));
//        executeCommand(new String[]{"git", "clone", "git@github.com:lzb-parent/parent-ui.git"}, new File(uiAdminDir, "src"));

        executeCommand(new String[]{"yarn", "install", "--silent"}, uiAdminDir);
    }

    // 通用命令执行方法
    private static void executeCommand(String[] command, File workingDir) throws IOException, InterruptedException {
        System.out.println("Executing: " + String.join(" ", command) + " in " + workingDir);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(workingDir); // 设置工作目录
        processBuilder.redirectErrorStream(true); // 合并标准输出和错误流

        Process process = processBuilder.start();

        // 处理输出
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code: " + exitCode);
        }
    }
}
