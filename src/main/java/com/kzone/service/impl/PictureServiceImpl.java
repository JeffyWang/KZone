package com.kzone.service.impl;

import com.kzone.bo.Picture;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.MongoConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.dao.MongoDao;
import com.kzone.service.PictureService;
//import com.kzone.util.PictureUtil;
import com.kzone.util.PictureUtil;
import com.kzone.util.StringUtil;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private MongoDao mongoDao;
    @Value("#{kzoneConfig['picture.ratio']}")
    private double ratio;
    @Value("#{kzoneConfig['small.picture.width']}")
    private int smallPictureWidth;
    @Value("#{kzoneConfig['small.picture.height']}")
    private int smallPictureHeight;
    @Value("#{kzoneConfig['middle.picture.width']}")
    private int middlePictureWidth;
    @Value("#{kzoneConfig['middle.picture.height']}")
    private int middlePictureHeight;
    @Value("#{kzoneConfig['big.picture.width']}")
    private int bigPictureWidth;
    @Value("#{kzoneConfig['big.picture.height']}")
    private int bigPictureHeight;
    @Value("#{kzoneConfig['picture.base.url']}")
    private String baseURL;

    @Override
    public void addKTVPicture(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception {
        String picturePath = Thread.currentThread().getContextClassLoader().getResource("picture").getPath() + pictureName + "." + contentType;
        OutputStream os = new FileOutputStream(picturePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<InputStream>> results = new ArrayList<Future<InputStream>>();
        results.add(0,exec.submit(new PictureUtil(pictureName, ratio, smallPictureWidth, smallPictureHeight, contentType)));
        results.add(1,exec.submit(new PictureUtil(pictureName, ratio, middlePictureWidth, middlePictureHeight, contentType)));
        results.add(2,exec.submit(new PictureUtil(pictureName, ratio, bigPictureWidth, bigPictureHeight, contentType)));
        exec.shutdown();

        Picture pictures = null;
        for(int i = 0; i < results.size(); i ++) {
            try {
                Map<String, String> pictureMeteData = new HashMap<String, String>();
                pictureMeteData.put(CommonConstants.PICTURE_TYPE, type);
                pictureMeteData.put(CommonConstants.PICTURE_REF_ID, id);
                mongoDao.store(results.get(i).get(), pictureName + "_" + i, contentType, pictureMeteData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        inputStream.close();
        os.flush();
        os.close();

        File file = new File(picturePath);
        file.delete();
    }

    @Override
    public GridFSDBFile getPicture(String type, String name,int id) {
        GridFSDBFile result = mongoDao.find(new Query().addCriteria(Criteria.where(ParamsConstants.PARAM_MONGO_FILE_NAME).is(name)
                .andOperator(Criteria.where(MongoConstants.MONGO_METADATA_PICTURE_TYPE).is(type).and(MongoConstants.MONGO_METADATA_REF_ID).is(String.valueOf(id))))).get(0);
//                .andOperator(Criteria.where(MongoConstants.MONGO_METADATA_REF_ID).is(id))))).get(0);
        return result;
    }

    @Override
    public void deletePicture(String name) {
        mongoDao.deleteFile(new Query().addCriteria(Criteria.where(ParamsConstants.PARAM_MONGO_FILE_NAME).is(name)));
    }

    @Override
    public void deletePicture(String type, int id) {
        mongoDao.deleteFile(new Query().addCriteria(Criteria.where(MongoConstants.MONGO_METADATA_PICTURE_TYPE).is(type).and(MongoConstants.MONGO_METADATA_REF_ID).is(id)));
    }

    @Override
    public String addKtvPictureName(String picture, String pictureName, int KTVId) {
        Picture pic = null;

        try {
            pic = StringUtil.jsonStringToObject(picture, Picture.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sp = pic.getSmallPictures() + baseURL + CommonConstants.PICTURE_TYPE_KTV + "/" + KTVId + "/" + pictureName + "_0,";
        String mp = pic.getMiddlePictures() +  baseURL + CommonConstants.PICTURE_TYPE_KTV + "/" + KTVId + "/" + pictureName + "_1,";
        String bp = pic.getBigPictures() + baseURL + CommonConstants.PICTURE_TYPE_KTV + "/" + KTVId + "/" + pictureName + "_2,";
        pic.setSmallPictures(sp);
        pic.setMiddlePictures(mp);
        pic.setBigPictures(bp);

        return StringUtil.objectToJSONString(pic);
    }

    @Override
    public Map addInformationPicture(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception {
        String picturePath = Thread.currentThread().getContextClassLoader().getResource("picture").getPath() + pictureName + "." + contentType;
        OutputStream os = new FileOutputStream(picturePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<InputStream>> results = new ArrayList<Future<InputStream>>();
        results.add(0,exec.submit(new PictureUtil(pictureName, ratio, middlePictureWidth, middlePictureHeight, contentType)));
        exec.shutdown();

        Picture pictures = null;
        for(int i = 0; i < results.size(); i ++) {
            try {
                Map<String, String> pictureMeteData = new HashMap<String, String>();
                pictureMeteData.put(CommonConstants.PICTURE_TYPE, type);
                pictureMeteData.put(CommonConstants.PICTURE_REF_ID, id);
                mongoDao.store(results.get(i).get(), pictureName + "_1", contentType, pictureMeteData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        inputStream.close();
        os.flush();
        os.close();

        File file = new File(picturePath);
        file.delete();

        Map<String, String> pictureUrl = new HashMap<String, String>();
        pictureUrl.put("pictureUrl", baseURL + CommonConstants.PICTURE_TYPE_INFORMATION + "/" + id + "/" + pictureName + "_1");

        return pictureUrl;
    }

    public Map addGamePicture(InputStream inputStream, String pictureName, String contentType, String type, String id) throws Exception {
        String picturePath = Thread.currentThread().getContextClassLoader().getResource("picture").getPath() + pictureName + "." + contentType;
        OutputStream os = new FileOutputStream(picturePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<InputStream>> results = new ArrayList<Future<InputStream>>();
        results.add(0,exec.submit(new PictureUtil(pictureName, ratio, bigPictureWidth, bigPictureHeight, contentType)));
        exec.shutdown();

        Picture pictures = null;
        for(int i = 0; i < results.size(); i ++) {
            try {
                Map<String, String> pictureMeteData = new HashMap<String, String>();
                pictureMeteData.put(CommonConstants.PICTURE_TYPE, type);
                pictureMeteData.put(CommonConstants.PICTURE_REF_ID, id);
                mongoDao.store(results.get(i).get(), pictureName + "_2", contentType, pictureMeteData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        inputStream.close();
        os.flush();
        os.close();

        File file = new File(picturePath);
        file.delete();

        Map<String, String> pictureUrl = new HashMap<String, String>();
        pictureUrl.put("pictureUrl", baseURL + CommonConstants.PICTURE_TYPE_GAME + "/" + id + "/" + pictureName + "_2");

        return pictureUrl;
    }
}
