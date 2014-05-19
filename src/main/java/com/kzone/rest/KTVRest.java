package com.kzone.rest;

import com.kzone.bean.KTV;
import com.kzone.bo.Response;
import com.kzone.constants.ErrorCode;
import com.kzone.service.KTVService;
import com.kzone.service.PictureService;
import com.mongodb.gridfs.GridFSDBFile;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

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
    public Response getKTV(@PathParam("id") int id) {
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
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKTVsPage(@Context UriInfo uriInfo) {
        Response response = new Response();
        return response;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKTV(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }

}
