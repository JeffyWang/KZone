package com.kzone.rest;

import com.kzone.bean.Statistics;
import com.kzone.bo.Response;
import com.kzone.constants.ErrorCode;
import com.kzone.service.StatisticsService;
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
@Path("/statistics")
public class StatisticsRest {
    Logger log = Logger.getLogger(StatisticsRest.class);
    @Autowired
    private StatisticsService statisticsService;

    @GET
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response getStatistics(@PathParam("id") int id) {
        Response response = new Response();
        Statistics statistics = null;

        try {
            statistics = statisticsService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_STATISTICS_ERR_CODE, ErrorCode.GET_STATISTICS_ERR_MSG + e.getMessage());
        }

        response.setData(statistics);
        return response;
    }

    @GET
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response getStatisticss() {
        Response response = new Response();
        List<Statistics> statisticsList = null;

        try {
            statisticsList = statisticsService.getList();
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_STATISTICS_LIST_ERR_CODE, ErrorCode.GET_STATISTICS_LIST_ERR_MSG + e.getMessage());
        }

        response.setData(statisticsList);
        return response;
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces("application/json;charset=utf-8")
    public Response getStatisticssPage(@Context UriInfo uriInfo) {
        return null;
    }

    @POST
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response addStatistics(@RequestBody String body) {
        Response response = new Response();
        return response;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response deleteStatistics(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }
}
