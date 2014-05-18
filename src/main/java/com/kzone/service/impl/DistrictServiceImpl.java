package com.kzone.service.impl;

import com.kzone.bean.District;
import com.kzone.service.DistrictService;
import org.springframework.stereotype.Service;

/**
 * Created by Jeffy on 14-5-17
 */
@Service
public class DistrictServiceImpl extends CommonServiceImpl<District> implements DistrictService {
    @Override
    public void validateDistrict(District district) {
    }
}
