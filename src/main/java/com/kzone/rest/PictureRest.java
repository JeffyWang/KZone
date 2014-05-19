package com.kzone.rest;

import com.kzone.bean.KTV;
import com.kzone.bo.Response;
import com.kzone.service.PictureService;
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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
@Component
@Path("/picture")
public class PictureRest {
    @Autowired
    private PictureService pictureService;

    @POST
    @Path("/{pictureType}/{name}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKTV(@Context HttpServletRequest request, @PathParam("name") String name,  @PathParam("pictureType") String type) {
        Response response = new Response();

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("file");

        Map<String, String> pictureType = new HashMap<String, String>();
        pictureType.put("pictureType", type);

        try {
            InputStream input = file.getInputStream();
            pictureService.addPicture(input, name, "jpg", pictureType);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public InputStream getKTVPictures (@PathParam("name") String name) {
        Response response = new Response();
        InputStream inputStream = null;

        try {
            inputStream = pictureService.getPicture(name).getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
