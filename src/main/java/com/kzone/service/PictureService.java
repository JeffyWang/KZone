package com.kzone.service;

import com.kzone.bean.KTV;
import com.kzone.bo.Picture;
import com.mongodb.gridfs.GridFSDBFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
public interface PictureService {
    public Picture addPicture(InputStream inputStream, String fileName, String contentType, Object obj);

    public GridFSDBFile getPicture(String name);

    public KTV addPictureName(KTV ktv, Picture picture);
}
