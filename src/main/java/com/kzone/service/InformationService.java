package com.kzone.service;

import com.kzone.bean.Information;

/**
 * Created by Jeffy on 14-5-17
 */
public interface InformationService extends CommonService<Information> {
    public void validateInformation(Information information);
}
