package newproject;

import java.io.File;

public class RenameFilesAndDirs {
    public static void main(String[] args) {
        File rootDir = new File("/Users/zubin/IdeaProjects/snowball");

        // 递归处理文件夹和文件
        renameFilesAndDirs(rootDir);
    }

    private static void renameFilesAndDirs(File dir) {
        // 如果是文件夹
        if (dir.isDirectory()) {
            // 修改文件夹名称
            String newDirName = dir.getName().replace("platform", "snowball").replace("platform", "Snowball");
            File renamedDir = new File(dir.getParent(), newDirName);
            if (!dir.renameTo(renamedDir)) {
                System.out.println("无法重命名文件夹: " + dir.getPath());
            } else {
                System.out.println("文件夹重命名为: " + renamedDir.getPath());
            }

            // 递归处理文件夹中的文件
            File[] files = renamedDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    renameFilesAndDirs(file);
                }
            }
        } else {
            // 修改文件名称
            String newFileName = dir.getName().replace("platform", "snowball").replace("platform", "Snowball");
            File renamedFile = new File(dir.getParent(), newFileName);
            if (!dir.renameTo(renamedFile)) {
                System.out.println("无法重命名文件: " + dir.getPath());
            } else {
                System.out.println("文件重命名为: " + renamedFile.getPath());
            }
        }
    }
}
