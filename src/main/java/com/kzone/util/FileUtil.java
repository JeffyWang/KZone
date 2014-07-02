package com.kzone.util;

import com.kzone.service.FileService;

import java.io.*;

/**
 * Created by Jeffy on 2014/7/2 0002.
 */
public class FileUtil {
    public static boolean upload(InputStream inputStream,String filePath) throws Exception {
        OutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            os.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            inputStream.close();
            os.close();
        }

        return true;
    }

    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if(file.isFile()) {
            file.delete();
        } else {
            return false;
        }

        return true;
    }
}
