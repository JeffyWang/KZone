package com.kzone.service;

import com.mongodb.gridfs.GridFSDBFile;

import java.io.InputStream;

/**
 * Created by jeffy on 2014/6/8 0008.
 */
public interface FileService {
    public String addFile(InputStream inputStream, String fileName, String contentType) throws Exception;

    public void deleteFile(String fileName, String contentType);
}
