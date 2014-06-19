package com.kzone.service.impl;

import com.kzone.dao.InitializeDao;
import com.kzone.service.InitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jeffy on 2014/6/19 0019.
 */
@Service
public class InitializeServiceImpl implements InitializeService {
    @Autowired
    private InitializeDao initializeDao;

    @Override
    public void initialize(String hql) {
        initializeDao.executeHQL(hql);
    }
}
