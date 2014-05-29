package com.kzone.rest;

import com.kzone.bean.KTV;
import com.kzone.bo.ErrorMessage;
import com.kzone.bo.Picture;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.KTVService;
import com.kzone.service.PictureService;
import com.kzone.util.Pinyin4jUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
@Component
@Path("/picture")
public class PictureRest {
    Logger log = Logger.getLogger(PictureRest.class);
    @Autowired
    private PictureService pictureService;
    @Autowired
    private KTVService ktvService;

    @POST
    @Path("/ktv/{ktvId}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKTV(@Context HttpServletRequest request, @PathParam(ParamsConstants.PARAM_KTV_ID) int ktvId) {
        String picture = null;
        KTV ktv = null;
        String pictureName = "";

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("Filedata");

        try {
            ktv =  ktvService.get(ktvId);
            String tmp = Pinyin4jUtil.getPinyinJianPin(ktv.getName().replaceAll("[0-9]","")).split(",")[0];
            pictureName = tmp + "_" + ktv.getDistrictId();
            InputStream input = file.getInputStream();
            pictureService.addPicture(input, pictureName, CommonConstants.CONTENT_TYPE, CommonConstants.PICTURE_TYPE_KTV, String.valueOf(ktvId));
            picture = pictureService.addPictureName(ktv.getPictures(),pictureName);
            ktv.setPictures(picture);
            ktvService.update(ktv);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            pictureService.deletePicture(pictureName + "_0");
            pictureService.deletePicture(pictureName + "_1");
            pictureService.deletePicture(pictureName + "_2");
            return javax.ws.rs.core.Response.ok(new ErrorMessage(ErrorCode.ADD_PICTURE_ERR_CODE, ErrorCode.ADD_PICTURE_ERR_MSG), MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public InputStream getKTVPictures (@PathParam(ParamsConstants.PARAM_NAME) String name) {
        InputStream inputStream = null;

        try {
            inputStream = pictureService.getPicture(name).getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePictures (@PathParam(ParamsConstants.PARAM_NAME) String name) {
        try {
            log.debug("delete a picture, the picture's name is [" + name + "]");
            pictureService.deletePicture(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok(new com.kzone.bo.Response(), MediaType.APPLICATION_JSON).build();
    }

}
