package com.kzone.rest;

import com.kzone.bean.Statistics;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.service.StatisticsService;
import com.kzone.util.EncodingUtil;
import com.kzone.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistics(@PathParam("id") int id) {
        Statistics statistics = null;

        try {
            statistics = statisticsService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_STATISTICS_ERR_CODE, ErrorCode.GET_STATISTICS_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        return Response.ok(statistics, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatisticss() {
        List<Statistics> statisticsList = null;

        try {
            statisticsList = statisticsService.getList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_STATISTICS_LIST_ERR_CODE, ErrorCode.GET_STATISTICS_LIST_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        return Response.ok(statisticsList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatisticssPage(@Context UriInfo uriInfo) {
        return null;
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStatistics(@RequestBody String body) {
        Statistics statistics = null;
        String encode = EncodingUtil.getEncoding(body);

        try {
            body = new String(body.getBytes(encode), CommonConstants.ENCODE);
            statistics = StringUtil.jsonStringToObject(body, Statistics.class);
            statistics = statisticsService.add(statistics);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_STATISTICS_ERR_CODE, ErrorCode.ADD_STATISTICS_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();

        }
        return Response.ok(statistics, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStatistics(@PathParam("id") int id) {
        return null;
    }
}
