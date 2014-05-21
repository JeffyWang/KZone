package com.kzone.rest;

import com.kzone.bean.Area;
import com.kzone.bean.City;
import com.kzone.bean.Comment;
import com.kzone.bean.Province;
import com.kzone.bo.Response;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.DistrictService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
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
        Response response = new Response();
        List<Province> provinceList = null;

        try {
            provinceList = districtService.getProvinceList();
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(provinceList);
        return response;
    }

    @GET
    @Path("/province/info/{provinceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvince(@PathParam(ParamsConstants.DISTRICT_PROVINCE_ID) String provinceId) {
        Response response = new Response();
        Province province = null;

        try {
            province = districtService.getProvince(provinceId);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(province);
        return response;
    }

    @GET
    @Path("/city/info/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCity() {
        Response response = new Response();
        List<City> cityList = null;

        try {
            cityList = districtService.getCityList();
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(cityList);
        return response;
    }

    @GET
    @Path("/city/info/{cityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCity(@PathParam(ParamsConstants.DISTRICT_CITY_ID)String cityId) {
        Response response = new Response();
        City city = null;

        try {
            city = districtService.getCity(cityId);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(city);
        return response;
    }

    @GET
    @Path("/province/city/info/{provinceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityForProvince(@PathParam(ParamsConstants.DISTRICT_PROVINCE_ID) String provinceId) {
        Response response = new Response();
        List<City> cityList = null;

        try {
            cityList = districtService.getCityList(provinceId);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(cityList);
        return response;
    }

    @GET
    @Path("/area/info/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArea() {
        Response response = new Response();
        List<Area> areaList = null;

        try {
            areaList = districtService.getAreaList();
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(areaList);
        return response;
    }

    @GET
    @Path("/area/info/{areaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArea(@PathParam(ParamsConstants.DISTRICT_AREA_ID) String areaId) {
        Response response = new Response();
        Area area = null;

        try {
            area = districtService.getArea(areaId);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(area);
        return response;
    }

    @GET
    @Path("/city/area/info/{cityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAreaForCity(@PathParam(ParamsConstants.DISTRICT_CITY_ID) String cityId) {
        Response response = new Response();
        List<Area> areaList = null;

        try {
            areaList = districtService.getAreaList(cityId);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG + e.getMessage());
        }

        response.setData(areaList);
        return response;
    }
}
