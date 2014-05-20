package com.kzone.service;

import com.kzone.bean.Area;
import com.kzone.bean.City;
import com.kzone.bean.Province;

import java.util.List;

/**
 * Created by Jeffy on 14-5-17
 */
public interface DistrictService {

    public List<Province> getProvinceList() throws Exception;

    public Province getProvince(String provinceId) throws Exception;

    public List<City> getCityList() throws Exception;

    public List<City> getCityList(String provinceId) throws Exception;

    public City getCity(String cityId) throws Exception;

    public List<Area> getAreaList() throws Exception;

    public List<Area> getAreaList(String cityId) throws Exception;

    public Area getArea(String areaId) throws Exception;
}
