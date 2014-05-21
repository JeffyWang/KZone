package com.kzone.rest;

import com.kzone.bean.KTV;
import com.kzone.bo.Response;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.KTVService;
import com.kzone.service.PictureService;
import com.kzone.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
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
    @Autowired
    private PictureService pictureService;

    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTV(@PathParam(ParamsConstants.PARAM_ID) int id) {
        Response response = new Response();
        KTV ktv = null;

        try {
            ktv = ktvService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_KTV_ERR_CODE, ErrorCode.GET_KTV_ERR_MSG + e.getMessage());
        }

        response.setData(ktv);
        return response;
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVs() {
        Response response = new Response();
        List<KTV> ktvList = null;

        try {
            ktvList = ktvService.getList();
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_KTV_LIST_ERR_CODE, ErrorCode.GET_KTV_LIST_ERR_MSG + e.getMessage());
        }

        response.setData(ktvList);
        return response;
    }

    @GET
    @Path("/info/{offset}/{length}/{name}/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVsPage(@Context UriInfo uriInfo) {
        Response response = new Response();
        List<KTV> ktvPageList = null;

        MultivaluedMap<String, String> params = uriInfo.getPathParameters();
        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();
        int offset = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_OFFSET));
        int length = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_LENGTH));

        if (params.getFirst(ParamsConstants.PARAM_KTV_NAME) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PARAM_KTV_NAME))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PARAM_KTV_NAME)))
            likeCondition.put(ParamsConstants.PARAM_KTV_NAME, params.getFirst(ParamsConstants.PARAM_KTV_NAME));
        // 模糊查询条件健值对
        if (params.getFirst(ParamsConstants.PARAM_KTV_ADDRESS) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PARAM_KTV_ADDRESS))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PARAM_KTV_ADDRESS)))
            likeCondition.put(ParamsConstants.PARAM_KTV_ADDRESS, params.getFirst(ParamsConstants.PARAM_KTV_ADDRESS));

        try {
            ktvPageList = ktvService.getListForPage(KTV.class, offset, length, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_KTV_LIST_ERR_CODE, ErrorCode.GET_KTV_LIST_ERR_MSG + e.getMessage());
        }

        response.setData(ktvPageList);
        return response;
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKTV(@RequestBody String body){
        Response response = new Response();
        KTV ktv = null;

        try {
            ktv = StringUtil.jsonStringToObject(body, KTV.class);
            ktv = ktvService.add(ktv);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.ADD_KTV_ERR_CODE, ErrorCode.ADD_KTV_ERR_MSG + e.getMessage());
        }

        response.setData(ktv);
        return response;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKTV(@PathParam(ParamsConstants.PARAM_ID) int id) {
        Response response = new Response();
        KTV ktv = null;

        try {
            ktv = ktvService.get(id);
            ktvService.delete(ktv);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.DELETE_KTV_ERR_CODE, ErrorCode.DELETE_KTV_ERR_MSG + e.getMessage());
        }

        response.setData(ktv);
        return response;
    }

    @PUT
    @Path("/info/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKTV(@RequestBody String body) {
        Response response = new Response();
        KTV ktv = null;

        try {
            ktv = StringUtil.jsonStringToObject(body, KTV.class);
            ktv = ktvService.update(ktv);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.UPDATE_KTV_ERR_CODE, ErrorCode.UPDATE_KTV_ERR_MSG + e.getMessage());
        }

        response.setData(ktv);
        return  response;
    }
}
