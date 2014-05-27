package com.kzone.rest;

import com.kzone.bean.Information;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.ErrorCode;
import com.kzone.service.InformationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

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
    public Response getInformation(@PathParam("id") int id) {
        Information information = null;

        try {
            information = informationService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_INFORMATION_ERR_CODE, ErrorCode.GET_INFORMATION_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(information, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformations() {
        List<Information> informationList = null;

        try {
            informationList = informationService.getList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_INFORMATION_LIST_ERR_CODE, ErrorCode.GET_INFORMATION_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(informationList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformationsPage(@Context UriInfo uriInfo) {
        return null;
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInformation(@RequestBody String body) {
        return null;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInformation(@PathParam("id") int id) {
        return null;
    }
}
