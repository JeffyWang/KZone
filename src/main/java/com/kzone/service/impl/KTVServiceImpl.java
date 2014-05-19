package com.kzone.service.impl;

import com.kzone.bean.KTV;
import com.kzone.dao.MongoDao;
import com.kzone.service.KTVService;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by Jeffy on 14-5-17
 */
@Service
public class KTVServiceImpl extends CommonServiceImpl<KTV> implements KTVService {
    @Override
    public void validateKTV(KTV ktv) {
    }
}
