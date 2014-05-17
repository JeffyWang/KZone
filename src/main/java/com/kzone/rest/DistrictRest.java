package com.kzone.rest;

import com.kzone.bean.Comment;
import com.kzone.bean.District;
import com.kzone.bo.Response;
import com.kzone.service.DistrictService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Jeffy on 14-5-17
 */
@Component
@Path("/district")
public class DistrictRest {
    Logger log = Logger.getLogger(DistrictRest.class);
    @Autowired
    private DistrictService districtService;

    @GET
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response getDistrict(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }

    @GET
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response getDistricts() {
        Response response = new Response();
        return response;
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces("application/json;charset=utf-8")
    public Response getDistrictsPage(@Context UriInfo uriInfo) {
        return null;
    }

    @POST
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response addDistrict(@RequestBody String body) {
        Response response = new Response();
        return response;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response deleteDistrict(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }
}
