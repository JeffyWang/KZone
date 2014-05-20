package com.kzone.service.impl;

import com.kzone.bean.Area;
import com.kzone.bean.City;
import com.kzone.bean.Province;
import com.kzone.constants.ParamsConstants;
import com.kzone.dao.AreaDao;
import com.kzone.dao.CityDao;
import com.kzone.dao.ProvinceDao;
import com.kzone.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 */
@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Province> getProvinceList() throws Exception {
        return provinceDao.getList();
    }

    @Override
    public Province getProvince(String provinceId) throws Exception {
        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.DISTRICT_PROVINCE_ID, provinceId);

        return provinceDao.get(equalCondition);
    }

    @Override
    public List<City> getCityList() throws Exception {
        return cityDao.getList();
    }

    @Override
    public List<City> getCityList(String provinceId) throws Exception {
        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.DISTRICT_ID_REFERENCE, provinceId);

        return cityDao.getListEqual(equalCondition);
    }

    @Override
    public City getCity(String cityId) throws Exception {
        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.DISTRICT_CITY_ID, cityId);

        return cityDao.get(equalCondition);
    }

    @Override
    public List<Area> getAreaList() throws Exception {
        return areaDao.getList();
    }

    @Override
    public List<Area> getAreaList(String cityId) throws Exception {
        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.DISTRICT_ID_REFERENCE, cityId);

        return areaDao.getListEqual(equalCondition);
    }

    @Override
    public Area getArea(String areaId) throws Exception {
        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.DISTRICT_AREA_ID, areaId);

        return areaDao.get(equalCondition);
    }
}
