package com.upyun.service;

import java.io.IOException;

/**
 * Created by Jeffy on 2014/7/2 0002.
 */
public interface PicBucketService {
    public String addSmallPicture(String picturePath, String uploadPath) throws IOException;

    public String addMiddlePicture(String picturePath, String uploadPath) throws IOException;

    public String addBigPicture(String picturePath, String uploadPath) throws IOException;
}
