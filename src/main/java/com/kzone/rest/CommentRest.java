package com.kzone.rest;

import com.kzone.bean.Comment;
import com.kzone.bean.User;
import com.kzone.bo.Response;
import com.kzone.service.CommentService;
import com.kzone.service.CommonService;
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
@Path("/comment")
public class CommentRest {
    Logger log = Logger.getLogger(CommentRest.class);
    @Autowired
    private CommentService commentService;

    @GET
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response getComment(@PathParam("id") int id) {
        Response response = new Response();
        try {
            Comment comment = commentService.get(id);
            response.setData(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response getComments() {
        Response response = new Response();
        try {
            List<Comment> commentList = commentService.getList();
            response.setData(commentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces("application/json;charset=utf-8")
    public Response getCommentsPage(@Context UriInfo uriInfo) {
        Response response = new Response();
        return response;
    }

    @POST
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response addComment(@RequestBody String body) {
        Response response = new Response();
        return response;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response deleteComment(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }
}
