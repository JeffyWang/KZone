package com.kzone.rest;

import com.kzone.bean.KTV;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.HTTPConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.KTVService;
import com.kzone.util.EncodingUtil;
import com.kzone.util.StringUtil;
import com.wordnik.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 */
@Component
@Path("/ktv")
@Api(value = "/ktv", description = "KTV相关接口")
public class KTVRest {
    Logger log = Logger.getLogger(KTVRest.class);
    @Autowired
    private KTVService ktvService;

    @GET
    @Path("/info/{id}")
    @ApiOperation(value = "通过KTV id查询KTV详细信息", notes = "传入一个KTV id，返回KTV的详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTV(
            @ApiParam(value = "KTV id", required = true)
            @PathParam(ParamsConstants.PARAM_ID) int id) {
        KTV ktv = null;

        try {
            ktv = ktvService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_KTV_ERR_CODE, ErrorCode.GET_KTV_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get a " + ktv.toString());
        return Response.ok(ktv,MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @ApiOperation(value = "查询KTV信息列表", notes = "传入参数，返回KTV信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVsPage(
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_OFFSET_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_OFFSET) int offset,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_LENGTH_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_LENGTH) int length,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_ORDER_DESC_MSG, required = false)
            @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc,
            @ApiParam(value = ParamsConstants.PARAM_KTV_NAME_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_NAME) String name,
            @ApiParam(value = ParamsConstants.PARAM_KTV_ADDRESS_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_ADDRESS) String address,
            @ApiParam(value = ParamsConstants.PARAM_KTV_DISTRICT_ID_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_DISTRICT_ID) String districtId) {
        List<KTV> ktvPageList = null;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (name != null
                && !CommonConstants.NULL_STRING.equals(name)
                && !CommonConstants.NULL.equals(name))
            likeCondition.put(ParamsConstants.PARAM_KTV_NAME, name);
        // 模糊查询条件健值对
        if (address != null
                && !CommonConstants.NULL_STRING.equals(address)
                && !CommonConstants.NULL.equals(address))
            likeCondition.put(ParamsConstants.PARAM_KTV_ADDRESS, address);
        if (districtId != null
                && !CommonConstants.NULL_STRING.equals(districtId)
                && !CommonConstants.NULL.equals(districtId))
            likeCondition.put(ParamsConstants.PARAM_KTV_DISTRICT_ID, districtId);

        try {
            ktvPageList = ktvService.getListForPage(KTV.class, offset, length, orderDesc, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_KTV_LIST_ERR_CODE, ErrorCode.GET_KTV_LIST_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get KTVs pages success.");
        return Response.ok(ktvPageList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info/page")
    @ApiOperation(value = "通过KTV id查询KTV详细信息", notes = "传入一个KTV id，返回KTV的详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVsPages(
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_PAGE_NUM_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_PAGE_NUM) int pageNum,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_PAGE_DATA_COUNT_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_PAGE_DATA_COUNT) int pageDataCount,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_ORDER_DESC_MSG, required = false)
            @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc,
            @ApiParam(value = ParamsConstants.PARAM_KTV_NAME_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_NAME) String name,
            @ApiParam(value = ParamsConstants.PARAM_KTV_ADDRESS_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_ADDRESS) String address,
            @ApiParam(value = ParamsConstants.PARAM_KTV_DISTRICT_ID_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_DISTRICT_ID) String districtId) {
        List<KTV> ktvPageList = null;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (name != null
                && !CommonConstants.NULL_STRING.equals(name)
                && !CommonConstants.NULL.equals(name))
            likeCondition.put(ParamsConstants.PARAM_KTV_NAME, name);
        // 模糊查询条件健值对
        if (address != null
                && !CommonConstants.NULL_STRING.equals(address)
                && !CommonConstants.NULL.equals(address))
            likeCondition.put(ParamsConstants.PARAM_KTV_ADDRESS, address);
        if (districtId != null
                && !CommonConstants.NULL_STRING.equals(districtId)
                && !CommonConstants.NULL.equals(districtId))
            likeCondition.put(ParamsConstants.PARAM_KTV_DISTRICT_ID, districtId);

        try {
            long dataCount = ktvService.getListCount(equalCondition, likeCondition);
            int pageCount = 0;

            if(dataCount % pageDataCount == 0)
                pageCount = (int) (dataCount / pageDataCount);
            else
                pageCount = (int) (dataCount / pageDataCount) + 1;

            int offset = pageNum * pageDataCount;
            int length = pageDataCount;

            ktvPageList = ktvService.getListForPage(KTV.class, offset, length, orderDesc, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_KTV_LIST_ERR_CODE, ErrorCode.GET_KTV_LIST_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get KTVs pages success.");
        return Response.ok(ktvPageList, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKTV(
            @RequestBody String body){
        KTV ktv = null;
        String encode = EncodingUtil.getEncoding(body);

        try {
            body = new String(body.getBytes(encode),CommonConstants.ENCODE);
            ktv = StringUtil.jsonStringToObject(body, KTV.class);
            ktv = ktvService.add(ktv);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_KTV_ERR_CODE, ErrorCode.ADD_KTV_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Add a " + ktv.toString());
        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKTV(
            @PathParam(ParamsConstants.PARAM_ID) int id) {
        KTV ktv = null;

        try {
            ktv = ktvService.get(id);
            ktvService.delete(ktv);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.DELETE_KTV_ERR_CODE, ErrorCode.DELETE_KTV_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Delete a " + ktv.toString());
        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/info/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKTV(
            @RequestBody String body) {
        KTV ktv = null;
        String encode = EncodingUtil.getEncoding(body);

        try {
            body = new String(body.getBytes(encode),CommonConstants.ENCODE);
            ktv = StringUtil.jsonStringToObject(body, KTV.class);
            ktv = ktvService.update(ktv);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.UPDATE_KTV_ERR_CODE, ErrorCode.UPDATE_KTV_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Update a " + ktv.toString());
        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/count")
    @ApiOperation(value = "查询KTV总数", notes = "查询KTV总数")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVCount(
            @ApiParam(value = ParamsConstants.PARAM_KTV_NAME_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_NAME) String name,
            @ApiParam(value = ParamsConstants.PARAM_KTV_ADDRESS_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_ADDRESS) String address,
            @ApiParam(value = ParamsConstants.PARAM_KTV_DISTRICT_ID_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_KTV_DISTRICT_ID) String districtId) {
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        int ktvCount = 0;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (name != null
                && !CommonConstants.NULL_STRING.equals(name)
                && !CommonConstants.NULL.equals(name))
            likeCondition.put(ParamsConstants.PARAM_KTV_NAME, name);
        // 模糊查询条件健值对
        if (address != null
                && !CommonConstants.NULL_STRING.equals(address)
                && !CommonConstants.NULL.equals(address))
            likeCondition.put(ParamsConstants.PARAM_KTV_ADDRESS, address);
        if (districtId != null
                && !CommonConstants.NULL_STRING.equals(districtId)
                && !CommonConstants.NULL.equals(districtId))
            likeCondition.put(ParamsConstants.PARAM_KTV_DISTRICT_ID, districtId);

        try {
            ktvCount = (int) ktvService.getListCount(equalCondition,likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.COUNT_KTV_ERR_CODE, ErrorCode.COUNT_KTV_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        countMap.put(ParamsConstants.PAGE_DATA_COUNT, ktvCount);
        log.debug("Get KTVs count is {" + ktvCount + "}");
        return Response.ok(countMap, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/picture/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKTVPictures(
            @PathParam(ParamsConstants.PARAM_ID) int id) {
        KTV ktv = null;

        try {
            ktv = ktvService.get(id);
            String picturesString = ktv.getPictures();
            JSONObject jsonObject = StringUtil.stringToJSON(picturesString);
            String smallPicturesString = (String) jsonObject.get(ParamsConstants.PARAM_SMALL_PICTURES);
            String middlePicturesString = (String) jsonObject.get(ParamsConstants.PARAM_MIDDLE_PICTURES);
            String bigPicturesString = (String) jsonObject.get(ParamsConstants.PARAM_BIG_PICTURES);
            String[] smallPictures = smallPicturesString.split(",");
            String[] middlePictures = middlePicturesString.split(",");
            String[] bigPictures = bigPicturesString.split(",");
            String[] pictureName = new String[smallPictures.length + middlePictures.length + bigPictures.length];
            System.arraycopy(smallPictures, 0, pictureName, 0, smallPictures.length);
            System.arraycopy(middlePictures, 0, pictureName, smallPictures.length, middlePictures.length);
            System.arraycopy(bigPictures, 0, pictureName, middlePictures.length + smallPictures.length, bigPictures.length);

            for(String s : pictureName) {
//                pictureService.deletePicture(s);
            }

        } catch (Exception e) {
            log.warn(e);
        }

//        pictureService
        return Response.ok().build();
    }
}
