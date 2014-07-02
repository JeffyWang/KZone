package com.upyun.service.impl;

import com.kzone.constants.CommonConstants;
import com.kzone.constants.HTTPConstants;
import com.upyun.service.PicBucketService;
import com.upyun.util.UpYun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PicBucketServiceImpl implements PicBucketService {

	// 运行前先设置好以下三个参数
	private String bucketName = "kzone";
	private String userName = "wjfdwr";
	private String userPassword = "wjfwjf456789";
    private boolean isDebug = true;
    private String smallPicture = "s";
    private String middlePicture = "m";
    private String bigPicture = "b";
    /** 绑定的域名 */
    private final String URL = HTTPConstants.HTTP_PROTOCAL_PATH + bucketName + CommonConstants.UPYUN_URI;

    @Override
    public boolean isExit(String picturePath) {
        File pictureFile = new File(picturePath);

        if(!pictureFile.isFile()) {
            return false;
        }
        return true;
    }

    @Override
    public String addSmallPicture(String picturePath, String uploadPath) throws IOException {
        UpYun upyun = new UpYun(bucketName, userName, userPassword);
        upyun.setDebug(isDebug);
        // 本地待上传的图片文件
        File picture = new File(picturePath);

        // 设置缩略图的参数
        Map<String, String> params = new HashMap<String, String>();

        // 设置缩略图的质量，默认 95
        params.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

        // 设置缩略图的锐化，默认锐化（true）
        params.put(UpYun.PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");

        // 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
        // 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
        params.put(UpYun.PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "s");

        // 上传文件，并自动创建父级目录（最多10级）
        boolean result = upyun.writeFile(uploadPath, picture, true, params);

        return URL + uploadPath;
    }

    @Override
    public String addMiddlePicture(String picturePath, String uploadPath) throws IOException {
        UpYun upyun = new UpYun(bucketName, userName, userPassword);
        upyun.setDebug(isDebug);
        // 本地待上传的图片文件
        File picture = new File(picturePath);

        // 设置缩略图的参数
        Map<String, String> params = new HashMap<String, String>();

        // 设置缩略图的质量，默认 95
        params.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

        // 设置缩略图的锐化，默认锐化（true）
        params.put(UpYun.PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");

        // 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
        // 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
        params.put(UpYun.PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "m");

        // 上传文件，并自动创建父级目录（最多10级）
        boolean result = upyun.writeFile(uploadPath, picture, true, params);

        return URL + uploadPath;
    }

    @Override
    public String addBigPicture(String picturePath, String uploadPath) throws IOException {
        UpYun upyun = new UpYun(bucketName, userName, userPassword);
        upyun.setDebug(isDebug);
        // 本地待上传的图片文件
        File picture = new File(picturePath);

        // 设置缩略图的参数
        Map<String, String> params = new HashMap<String, String>();

        // 设置缩略图的质量，默认 95
        params.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

        // 设置缩略图的锐化，默认锐化（true）
        params.put(UpYun.PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");

        // 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
        // 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
        params.put(UpYun.PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "b");

        // 上传文件，并自动创建父级目录（最多10级）
        boolean result = upyun.writeFile(uploadPath, picture, true, params);

        return URL + uploadPath;
    }
}
