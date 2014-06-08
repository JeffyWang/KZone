package com.kzone.service.impl;

import com.kzone.service.FileService;
import com.mongodb.gridfs.GridFSDBFile;

import java.io.InputStream;

/**
 * Created by jeffy on 2014/6/8 0008.
 */
public class FileServiceImpl implements FileService {
    @Override
    public void addFile(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception {

    }

    @Override
    public GridFSDBFile getFile(String name, int KTVId) {
        return null;
    }

    @Override
    public void deleteFile(String name) {

    }
}
