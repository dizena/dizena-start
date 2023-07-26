package org.dizena.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageZip {

    public static byte[] compressImage(InputStream sourceFile, int maxWidth, int maxHeight) throws IOException {
        BufferedImage sourceImage = ImageIO.read(sourceFile);

        // 计算缩放比例
        int newWidth = sourceImage.getWidth();
        int newHeight = sourceImage.getHeight();
        if (newWidth > maxWidth || newHeight > maxHeight) {
            double widthRatio = (double) maxWidth / newWidth;
            double heightRatio = (double) maxHeight / newHeight;
            double scaleFactor = Math.min(widthRatio, heightRatio);
            newWidth = (int) (newWidth * scaleFactor);
            newHeight = (int) (newHeight * scaleFactor);
        }

        // 创建缩放后的图像
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, sourceImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(sourceImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        // 保存缩放后的图像到目标文件
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", bos);
        return bos.toByteArray();
    }

    public static void compressImage(File sourceFile, File destinationFile, int maxWidth, int maxHeight) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFile);
        byte[] bs = compressImage(fis, maxWidth, maxHeight);
        FileOutputStream fos = new FileOutputStream(destinationFile);
        fos.write(bs);
        fos.flush();
        fos.close();
    }

    public static byte[] compressIcon(InputStream sourceFile) {
        try {
            int maxWidth = 400;
            int maxHeight = 400;
            return compressImage(sourceFile, maxWidth, maxHeight);
        } catch (IOException e) {

        }
        return null;
    }

    public static byte[] compress34(InputStream sourceFile) {
        try {
            int maxWidth = 480;
            int maxHeight = 640;
            return compressImage(sourceFile, maxWidth, maxHeight);
        } catch (IOException e) {

        }
        return null;
    }

    public static byte[] compress916(InputStream sourceFile) {
        try {
            int maxWidth = 720;
            int maxHeight = 1280;
            return compressImage(sourceFile, maxWidth, maxHeight);
        } catch (IOException e) {

        }
        return null;
    }

    public static byte[] compress1k(InputStream sourceFile) {
        try {
            int maxWidth = 1280;
            int maxHeight = 720;
            return compressImage(sourceFile, maxWidth, maxHeight);
        } catch (IOException e) {

        }
        return null;
    }

    public static byte[] compress2k(InputStream sourceFile) {
        try {
            int maxWidth = 1920;
            int maxHeight = 1080;
            return compressImage(sourceFile, maxWidth, maxHeight);
        } catch (IOException e) {

            return null;
        }
    }


}
