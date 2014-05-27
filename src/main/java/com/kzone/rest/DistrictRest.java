package com.kzone.rest;

import com.kzone.bean.Area;
import com.kzone.bean.City;
import com.kzone.bean.Province;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.DistrictService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Jeffy on 14-5-17
 */
@Component
@Path("/district")
public class DistrictRest {
    Logger log = Logger.getLogger(DistrictRest.class);
    @Autowired
    private DistrictService districtService;

    @GET
    @Path("/province/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvince() {
        List<Province> provinceList = null;

        try {
            provinceList = districtService.getProvinceList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(provinceList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/province/info/{provinceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvince(@PathParam(ParamsConstants.DISTRICT_PROVINCE_ID) String provinceId) {
        Province province = null;

        try {
            province = districtService.getProvince(provinceId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(province, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/city/info/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCity() {
        List<City> cityList = null;

        try {
            cityList = districtService.getCityList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(cityList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/city/info/{cityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCity(@PathParam(ParamsConstants.DISTRICT_CITY_ID)String cityId) {
        City city = null;

        try {
            city = districtService.getCity(cityId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(city, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/province/city/info/{provinceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityForProvince(@PathParam(ParamsConstants.DISTRICT_PROVINCE_ID) String provinceId) {
        List<City> cityList = null;

        try {
            cityList = districtService.getCityList(provinceId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(cityList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/area/info/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArea() {
        List<Area> areaList = null;

        try {
            areaList = districtService.getAreaList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(areaList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/area/info/{areaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArea(@PathParam(ParamsConstants.DISTRICT_AREA_ID) String areaId) {
        Area area = null;

        try {
            area = districtService.getArea(areaId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(area, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/city/area/info/{cityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAreaForCity(@PathParam(ParamsConstants.DISTRICT_CITY_ID) String cityId) {
        List<Area> areaList = null;

        try {
            areaList = districtService.getAreaList(cityId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(areaList, MediaType.APPLICATION_JSON).build();
    }
}
