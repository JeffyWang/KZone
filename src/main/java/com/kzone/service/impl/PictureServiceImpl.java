package com.kzone.service.impl;

import com.kzone.dao.MongoDao;
import com.kzone.service.PictureService;
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
    public void addPicture(InputStream inputStream, String fileName, String contentType, Object obj) {
        mongoDao.store(inputStream, fileName, contentType, obj);
    }

    @Override
    public GridFSDBFile getPicture(String name) {
        GridFSDBFile result = mongoDao.find(new Query().addCriteria(Criteria.where("filename").is(name))).get(0);
        return result;
    }
}
