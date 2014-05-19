package com.kzone.service;

import com.mongodb.gridfs.GridFSDBFile;

import java.io.InputStream;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
public interface PictureService {
    public void addPicture(InputStream inputStream, String fileName, String contentType, Object obj);

    public GridFSDBFile getPicture(String name);
}
