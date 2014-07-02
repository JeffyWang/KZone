package com.kzone.rest;

import com.kzone.bean.KTV;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.KTVService;
import com.kzone.util.EncodingUtil;
import com.kzone.util.StringUtil;
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
public class KTVRest {
    Logger log = Logger.getLogger(KTVRest.class);
    @Autowired
    private KTVService ktvService;

    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTV(@PathParam(ParamsConstants.PARAM_ID) int id) {
        KTV ktv = null;

        try {
            ktv = ktvService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_KTV_ERR_CODE, ErrorCode.GET_KTV_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        return Response.ok(ktv,MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVsPage(@QueryParam(ParamsConstants.PAGE_PARAMS_OFFSET) int offset, @QueryParam(ParamsConstants.PAGE_PARAMS_LENGTH) int length,
                                @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc, @QueryParam(ParamsConstants.PARAM_KTV_NAME) String name,
                                @QueryParam(ParamsConstants.PARAM_KTV_ADDRESS) String address, @QueryParam(ParamsConstants.PARAM_KTV_DISTRICT_ID) String districtId) {
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

        return Response.ok(ktvPageList, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKTV(@RequestBody String body){
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

        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKTV(@PathParam(ParamsConstants.PARAM_ID) int id) {
        KTV ktv = null;

        try {
            ktv = ktvService.get(id);
            ktvService.delete(ktv);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.DELETE_KTV_ERR_CODE, ErrorCode.DELETE_KTV_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/info/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKTV(@RequestBody String body) {
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

        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVCount(@QueryParam(ParamsConstants.PARAM_KTV_NAME) String name, @QueryParam(ParamsConstants.PARAM_KTV_ADDRESS) String address,
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
        return Response.ok(countMap, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/picture/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKTVPictures(@PathParam(ParamsConstants.PARAM_ID) int id) {
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
