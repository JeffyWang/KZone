package com.kzone.service.impl;

import com.kzone.service.FileService;
import com.kzone.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by jeffy on 2014/6/8 0008.
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String addFile(InputStream inputStream, String fileName, String contentType) throws Exception {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("picture").getPath() + fileName + "." + contentType;
        boolean isUpload = FileUtil.upload(inputStream, filePath);
        if(!isUpload)
            return null;
        return filePath;
    }

    @Override
    public void deleteFile(String fileName, String contentType) {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("picture").getPath() + fileName + "." + contentType;
        boolean isDeleted = FileUtil.delete(filePath);
    }
}
