package org.dizena.utils;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static byte[] readFile(String filepath) {
        try {
            return Files.readAllBytes(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFile(String filepath, byte[] bs) {
        try {
            Files.write(Paths.get(filepath), bs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createFile(byte[] bs, String name) {
        File file = new File(name);
        if (file.exists()) {
            boolean b1 = file.delete();
        }

        if (!file.getParentFile().exists()) {
            boolean b2 = file.getParentFile().mkdirs();
        }

        try {
            OutputStream outputStream = new FileOutputStream(name);
            IOUtils.write(bs, outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteFile(File file) {
        boolean b = file.delete();
    }

    public static void deleteFile(String name) {
        File file = new File(name);
        if (file.exists()) {
            boolean b = file.delete();
        }

    }

}
