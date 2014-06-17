package com.kzone.service;

import com.kzone.bean.KTV;
import com.kzone.bo.Picture;
import com.mongodb.gridfs.GridFSDBFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
public interface PictureService {
    public void addKTVPicture(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception;

    public GridFSDBFile getPicture(String type, String name, int id);

    public void deletePicture(String name);

    public void deletePicture(String type, int id);

    public String addKtvPictureName(String picture, String pictureName, int KTVId);

    public Map addInformationPicture(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception;

}
