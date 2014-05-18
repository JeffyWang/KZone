package com.kzone.service;

import com.kzone.bean.District;

/**
 * Created by Jeffy on 14-5-17
 */
public interface DistrictService extends CommonService<District> {
    public void validateDistrict(District district);
}
