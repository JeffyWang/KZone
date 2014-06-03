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
    public void addPicture(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception;

    public GridFSDBFile getPicture(String name, int KTVId);

    public void deletePicture(String name);

    public String addPictureName(String picture, String pictureName, int KTVId);
}
