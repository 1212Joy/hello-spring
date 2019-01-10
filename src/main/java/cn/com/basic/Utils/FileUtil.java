package cn.com.basic.Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by zhaijiayi on 2018/11/6.
 */
public class FileUtil {

    public static void saveFile(String content, String path) {
        try {
            //文件路径不存在，则创建
            FileUtils.write(new File(path), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("保存文件异常,文件路径为:");
        }
    }

    public static String readFileToString(File file) {

        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("读取文件异常,文件路径为:");
        }
    }

    public static byte[] readFileToStream(File file) {
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException("读取文件异常,文件路径为:");
        }
    }


}
