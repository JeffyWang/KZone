package com.kzone.rest;

import com.kzone.bo.Response;
import com.kzone.service.InformationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
    @Produces("application/json;charset=utf-8")
    public Response getInformation(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }

    @GET
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response getInformations() {
        Response response = new Response();
        return response;
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces("application/json;charset=utf-8")
    public Response getInformationsPage(@Context UriInfo uriInfo) {
        return null;
    }

    @POST
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response addInformation(@RequestBody String body) {
        Response response = new Response();
        return response;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response deleteInformation(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }
}
