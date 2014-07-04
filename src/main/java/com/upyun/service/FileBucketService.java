package com.upyun.service;

import java.util.List;

/**
 * Created by Jeffy on 2014/7/2 0002.
 */
public interface FileBucketService {
    public boolean isExit(String filePath);

    public List<String> readDir(String dirPath);

    public  boolean deleteFile(String filePath);

    public boolean deleteDir(String dirPath);
}
