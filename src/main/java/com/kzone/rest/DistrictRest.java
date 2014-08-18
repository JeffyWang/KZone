package com.kzone.rest;

import com.kzone.bean.Area;
import com.kzone.bean.City;
import com.kzone.bean.Province;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.HTTPConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.DistrictService;
import com.wordnik.swagger.annotations.*;
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
@Api(value = "/district", description = "区域相关接口")
public class DistrictRest {
    Logger log = Logger.getLogger(DistrictRest.class);
    @Autowired
    private DistrictService districtService;

    @GET
    @Path("/province/info")
    @ApiOperation(value = "获取省信息列表", notes = "获取省信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvince() {
        List<Province> provinceList = null;

        try {
            provinceList = districtService.getProvinceList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get province list success.");
        return Response.ok(provinceList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/province/info/{provinceId}")
    @ApiOperation(value = "获取省详细信息", notes = "传入一个省id，返回省详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvince(
            @ApiParam(value = "省id", required = true)
            @PathParam(ParamsConstants.DISTRICT_PROVINCE_ID) String provinceId) {
        Province province = null;

        try {
            province = districtService.getProvince(provinceId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get a " + province.toString());
        return Response.ok(province, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/city/info/")
    @ApiOperation(value = "获取市信息列表", notes = "获取市信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCity() {
        List<City> cityList = null;

        try {
            cityList = districtService.getCityList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get city list success.");
        return Response.ok(cityList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/city/info/{cityId}")
    @ApiOperation(value = "获取市详细信息", notes = "传入一个市id，返回市详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCity(
            @ApiParam(value = "市id", required = true)
            @PathParam(ParamsConstants.DISTRICT_CITY_ID)String cityId) {
        City city = null;

        try {
            city = districtService.getCity(cityId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get a " + city.toString());
        return Response.ok(city, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/province/city/info/{provinceId}")
    @ApiOperation(value = "获取省下所有市信息列表", notes = "传入一个省id，返回省下所有市信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityForProvince(
            @ApiParam(value = "省id", required = true)
            @PathParam(ParamsConstants.DISTRICT_PROVINCE_ID) String provinceId) {
        List<City> cityList = null;

        try {
            cityList = districtService.getCityList(provinceId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get the city of the province success, the province id is {" + provinceId + "}");
        return Response.ok(cityList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/area/info/")
    @ApiOperation(value = "获取区信息列表", notes = "获取区信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArea() {
        List<Area> areaList = null;

        try {
            areaList = districtService.getAreaList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get area list success.");
        return Response.ok(areaList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/area/info/{areaId}")
    @ApiOperation(value = "获取区详细信息", notes = "传入一个区id，返回区详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArea(
            @ApiParam(value = "区id", required = true)
            @PathParam(ParamsConstants.DISTRICT_AREA_ID) String areaId) {
        Area area = null;

        try {
            area = districtService.getArea(areaId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get a " + area.toString());
        return Response.ok(area, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/city/area/info/{cityId}")
    @ApiOperation(value = "获取市下所有区信息列表", notes = "传入一个市id，返回市下所有区信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAreaForCity(
            @ApiParam(value = "市id", required = true)
            @PathParam(ParamsConstants.DISTRICT_CITY_ID) String cityId) {
        List<Area> areaList = null;

        try {
            areaList = districtService.getAreaList(cityId);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_DISTRICT_ERR_CODE, ErrorCode.GET_DISTRICT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get the area of the city success, the city id is {" + cityId + "}");
        return Response.ok(areaList, MediaType.APPLICATION_JSON).build();
    }
}
