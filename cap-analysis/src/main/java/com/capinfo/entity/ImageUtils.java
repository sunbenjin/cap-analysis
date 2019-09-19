package com.capinfo.entity;

import java.io.*;

public class ImageUtils {
    public static byte[] getImageBytes(File file) throws IOException {
        byte imageBytes[] = null;
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte bytes[] = new byte[1024];
        int lens = -1;
        try {
            while ((lens = is.read(bytes)) != -1) {
                baos.write(bytes, 0, lens);
            }
            imageBytes = baos.toByteArray();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            baos.flush();
            baos.close();
            is.close();
        }
        return imageBytes;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getImageBytes(new File("D:/1.jpg")));
    }
}
