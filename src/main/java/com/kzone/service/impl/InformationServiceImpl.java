package com.kzone.service.impl;

import com.kzone.bean.Information;
import com.kzone.service.InformationService;
import org.springframework.stereotype.Service;

/**
 * Created by Jeffy on 14-5-17
 */
@Service
public class InformationServiceImpl extends CommonServiceImpl<Information> implements InformationService {
    @Override
    public void validateInformation(Information information) {
    }
}
