package com.kzone.service.impl;

import com.kzone.bean.KTV;
import com.kzone.service.KTVService;
import org.springframework.stereotype.Service;

/**
 * Created by Jeffy on 14-5-17
 */
@Service
public class KTVServiceImpl extends CommonServiceImpl<KTV> implements KTVService {
    @Override
    public void validateKTV(KTV ktv) {
    }
}
