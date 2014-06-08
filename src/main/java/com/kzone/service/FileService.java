package com.kzone.service;

import com.mongodb.gridfs.GridFSDBFile;

import java.io.InputStream;

/**
 * Created by jeffy on 2014/6/8 0008.
 */
public interface FileService {
    public void addFile(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception;

    public GridFSDBFile getFile(String name, int KTVId);

    public void deleteFile(String name);
}
