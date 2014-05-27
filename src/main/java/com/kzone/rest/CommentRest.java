package com.kzone.rest;

import com.kzone.bean.Comment;
import com.kzone.bean.KTV;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.CommentService;
import com.kzone.service.KTVService;
import com.kzone.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 */
@Component
@Path("/comment")
public class CommentRest {
    Logger log = Logger.getLogger(CommentRest.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    KTVService ktvService;

    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(@PathParam("id") int id) {
        Comment comment = null;

        try {
            comment = commentService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_COMMENT_ERR_CODE, ErrorCode.GET_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments() {
        List<Comment> commentList = null;

        try {
            commentList = commentService.getList();
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_COMMENT_LIST_ERR_CODE, ErrorCode.GET_COMMENT_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(commentList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info/{offset}/{length}/{score}/{comment}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsPage(@Context UriInfo uriInfo) {
        List<Comment> commentList = null;

        MultivaluedMap<String, String> params = uriInfo.getPathParameters();
        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> gtCondition = new HashMap<String, String>();
        int offset = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_OFFSET));
        int length = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_LENGTH));

        if (params.getFirst(ParamsConstants.PARAM_COMMENT_SCORE) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PARAM_COMMENT_SCORE))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PARAM_COMMENT_SCORE)))
            gtCondition.put(ParamsConstants.PARAM_COMMENT_SCORE, params.getFirst(ParamsConstants.PARAM_COMMENT_SCORE));
        // 模糊查询条件健值对
        if (params.getFirst(ParamsConstants.PARAM_COMMENT_COMMENT) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PARAM_COMMENT_COMMENT))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PARAM_COMMENT_COMMENT)))
            likeCondition.put(ParamsConstants.PARAM_COMMENT_COMMENT, params.getFirst(ParamsConstants.PARAM_COMMENT_COMMENT));

        try {
            commentList = commentService.getListForPage(Comment.class, offset, length, null, likeCondition, gtCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_COMMENT_LIST_ERR_CODE, ErrorCode.GET_COMMENT_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(commentList, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(@RequestBody String body) {
        Comment comment = null;
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        try {
            comment = StringUtil.jsonStringToObject(body, Comment.class);
            float serviceScore = Float.valueOf(comment.getServiceScore());
            float environmentScore = Float.valueOf(comment.getEnvironmentScore());
            float soundEffectsScore = Float.valueOf(comment.getSoundEffectsScore());
            float score = (float) (serviceScore * 0.3 + environmentScore * 0.3 + soundEffectsScore * 0.4);
            comment.setServiceScore(decimalFormat.format(serviceScore));
            comment.setEnvironmentScore(decimalFormat.format(environmentScore));
            comment.setSoundEffectsScore(decimalFormat.format(soundEffectsScore));
            comment.setScore(decimalFormat.format(score));
            comment = commentService.add(comment);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_COMMENT_ERR_CODE, ErrorCode.ADD_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        try {
            Map<String, Object> equalCondition = new HashMap<String, Object>();
            equalCondition.put(ParamsConstants.PARAM_COMMENT_KTV_ID, comment.getKTVId());
            float ktvScore = ktvService.countScore(commentService.getListEqual(equalCondition));
            KTV ktv = ktvService.get(comment.getKTVId());
            ktv.setScore(decimalFormat.format(ktvScore));
            ktvService.update(ktv);
        } catch (Exception e) {
            log.warn("count ktc score error : " + e);
        }

        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("id") int id) {
        return Response.ok("", MediaType.APPLICATION_JSON).build();
    }
}
