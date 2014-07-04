package com.upyun.service.impl;

import com.upyun.service.FileBucketService;
import com.upyun.util.UpYun;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffy on 2014/7/2 0002.
 */
@Service
public class FileBucketServiceImpl implements FileBucketService {
    // 运行前先设置好以下三个参数
    private String bucketName = "kzone";
    private String userName = "wjfdwr";
    private String userPassword = "wjfwjf456789";
    private boolean isDebug = true;
    UpYun upyun = null;

    public FileBucketServiceImpl() {
        upyun = new UpYun(bucketName, userName, userPassword);
    }

    @Override
    public boolean isExit(String filePath) {
        File pictureFile = new File(filePath);

        if(!pictureFile.isFile()) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> readDir(String dirPath) {
        List<String> fileNameList = new ArrayList<String>();
        List<UpYun.FolderItem> items = upyun.readDir(dirPath);

        if (null == items) {
            System.out.println("'" + dirPath + "'目录下没有文件。");
        } else {
            for (int i = 0; i < items.size(); i++) {
                fileNameList.add(items.get(i).name);
            }
        }

        return fileNameList;
    }

    @Override
    public boolean deleteFile(String filePath) {
        return upyun.deleteFile(filePath);
    }

    @Override
    public boolean deleteDir(String dirPath) {
        return upyun.rmDir(dirPath);
    }

}
