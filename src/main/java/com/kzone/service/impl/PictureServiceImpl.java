package com.kzone.service.impl;

import com.kzone.bean.KTV;
import com.kzone.bo.Picture;
import com.kzone.dao.MongoDao;
import com.kzone.service.PictureService;
import com.kzone.util.Test;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private MongoDao mongoDao;

    @Override
    public Picture addPicture(InputStream inputStream, String fileName, String contentType, Object obj) {
//        inputStream =  Test.test123(inputStream);
        mongoDao.store(inputStream, fileName, contentType, obj);
        return null;
    }

    @Override
    public GridFSDBFile getPicture(String name) {
        GridFSDBFile result = mongoDao.find(new Query().addCriteria(Criteria.where("filename").is(name))).get(0);
        return result;
    }

    @Override
    public KTV addPictureName(KTV ktv, Picture picture) {
        return null;
    }
}
