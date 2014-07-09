package com.kzone.rest;

import com.kzone.bean.Information;
import com.kzone.bo.Article;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.InformationService;
import com.kzone.util.EncodingUtil;
import com.kzone.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Jeffy on 14-5-17
 */
@Component
@Path("/information")
public class InformationRest {
    Logger log = Logger.getLogger(InformationRest.class);
    @Autowired
    private InformationService informationService;

    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformation(@PathParam(ParamsConstants.PARAM_ID) int id) {
        Information information = null;

        try {
            information = informationService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_INFORMATION_ERR_CODE, ErrorCode.GET_INFORMATION_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get a " + information.toString());
        return Response.ok(information, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformationsPage(@QueryParam(ParamsConstants.PAGE_PARAMS_OFFSET) int offset, @QueryParam(ParamsConstants.PAGE_PARAMS_LENGTH) int length,
                                        @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc,@QueryParam(ParamsConstants.PARAM_INFORMATION_TITLE) String title) {
        List<Information> informationsPageList = null;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (title != null
                && !CommonConstants.NULL_STRING.equals(title)
                && !CommonConstants.NULL.equals(title))
            likeCondition.put(ParamsConstants.PARAM_INFORMATION_TITLE, title);

        try {
            informationsPageList = informationService.getListForPage(Information.class, offset, length,orderDesc, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_INFORMATION_LIST_ERR_CODE, ErrorCode.GET_INFORMATION_LIST_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get information pages success.");
        return Response.ok(informationsPageList, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInformation(@RequestBody String body) {
        Information information = null;
        String encode = EncodingUtil.getEncoding(body);

        try {
            body = new String(body.getBytes(encode), CommonConstants.ENCODE);
            information = StringUtil.jsonStringToObject(body, Information.class);
            String article = StringUtil.stringToHtml(information.getTitle(), information.getArticle());
            information.setArticle(article);
            informationService.update(information);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_INFORMATION_ERR_CODE, ErrorCode.ADD_INFORMATION_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Update a " + information.toString());
        return Response.ok(information, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newInformation(@RequestBody String body) {
        Information information = new Information("infor"," "," ");

        try {
            informationService.add(information);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_INFORMATION_ERR_CODE, ErrorCode.ADD_INFORMATION_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Add a " + information.toString());
        return Response.ok(information, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInformation(@PathParam("id") int id) {
        Information information = null;

        try {
            information = informationService.get(id);
            informationService.delete(information);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.DELETE_INFORMATION_ERR_CODE, ErrorCode.DELETE_INFORMATION_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Delete a " + information.toString());
        return Response.ok(information, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformationCount( @QueryParam(ParamsConstants.PARAM_INFORMATION_TITLE) String title) {
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        int informationCount = 0;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (title != null
                && !CommonConstants.NULL_STRING.equals(title)
                && !CommonConstants.NULL.equals(title))
            likeCondition.put(ParamsConstants.PARAM_INFORMATION_TITLE, title);

        try {
            informationCount = (int) informationService.getListCount(equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.COUNT_INFORMATION_ERR_CODE, ErrorCode.COUNT_INFORMATION_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        countMap.put(ParamsConstants.PAGE_DATA_COUNT, informationCount);
        log.debug("Get information's count is {" + informationCount + "}");
        return Response.ok(countMap, MediaType.APPLICATION_JSON).build();
    }
}
