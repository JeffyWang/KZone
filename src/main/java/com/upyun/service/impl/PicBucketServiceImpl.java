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

/**
 * Created by Jeffy on 2014/7/2 0002.
 */
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
    UpYun upyun = null;

    public PicBucketServiceImpl() {
        upyun = new UpYun(bucketName, userName, userPassword);
    }

    @Override
    public String addSmallPicture(String picturePath, String uploadPath) throws IOException {
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

    public String addSamplePicture(String picturePath, String uploadPath) throws IOException {
        // 本地待上传的图片文件
        File file = new File(picturePath);

        // 设置待上传文件的 Content-MD5 值
        // 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
        upyun.setContentMD5(UpYun.md5(file));

        // 设置待上传文件的"访问密钥"
        // 注意：
        // 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
        // 举例：
        // 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
        // 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
        upyun.setFileSecret("kzonepic");

        // 上传文件，并自动创建父级目录（最多10级）
        boolean result = upyun.writeFile(uploadPath, file, true);

        return URL + uploadPath + "_b";
    }
}
