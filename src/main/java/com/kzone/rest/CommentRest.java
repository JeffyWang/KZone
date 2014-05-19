package com.kzone.rest;

import com.kzone.bean.Comment;
import com.kzone.bean.User;
import com.kzone.bo.Response;
import com.kzone.constants.ErrorCode;
import com.kzone.service.CommentService;
import com.kzone.service.CommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(@PathParam("id") int id) {
        Response response = new Response();
        Comment comment = null;

        try {
            comment = commentService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_COMMENT_ERR_CODE, ErrorCode.GET_COMMENT_ERR_MSG + e.getMessage());
        }

        response.setData(comment);
        return response;
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments() {
        Response response = new Response();
        List<Comment> commentList = null;

        try {
            commentList = commentService.getList();
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_COMMENT_LIST_ERR_CODE, ErrorCode.GET_COMMENT_LIST_ERR_MSG + e.getMessage());
        }

        response.setData(commentList);
        return response;
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsPage(@Context UriInfo uriInfo) {
        Response response = new Response();
        return response;
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(@RequestBody String body) {
        Response response = new Response();
        return response;
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("id") int id) {
        Response response = new Response();
        return response;
    }
}
