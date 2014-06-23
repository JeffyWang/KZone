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
            return Response.ok(new ErrorMessage(ErrorCode.GET_COMMENT_ERR_CODE, ErrorCode.GET_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsPage(@QueryParam(ParamsConstants.PAGE_PARAMS_OFFSET) int offset, @QueryParam(ParamsConstants.PAGE_PARAMS_LENGTH) int length,
                                    @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc, @QueryParam(ParamsConstants.PARAM_COMMENT_SCORE) String score,
                                    @QueryParam(ParamsConstants.PARAM_COMMENT_COMMENT) String comment, @QueryParam(ParamsConstants.PARAM_COMMENT_KTV_ID) String ktvId) {
        List<Comment> commentList = null;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();
        Map<String, String> gtCondition = new HashMap<String, String>();

        if (score != null
                && !CommonConstants.NULL_STRING.equals(score)
                && !CommonConstants.NULL.equals(score))
            gtCondition.put(ParamsConstants.PARAM_COMMENT_SCORE,score);
        // 模糊查询条件健值对
        if (comment != null
                && !CommonConstants.NULL_STRING.equals(comment)
                && !CommonConstants.NULL.equals(comment))
            likeCondition.put(ParamsConstants.PARAM_COMMENT_COMMENT, comment);
        if (ktvId != null
                && !CommonConstants.NULL_STRING.equals(ktvId)
                && !CommonConstants.NULL.equals(ktvId))
            equalCondition.put(ParamsConstants.PARAM_COMMENT_KTV_ID, ktvId);

        try {
            commentList = commentService.getListForPage(Comment.class, offset, length, orderDesc, equalCondition, likeCondition, gtCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_COMMENT_LIST_ERR_CODE, ErrorCode.GET_COMMENT_LIST_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
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
            return Response.ok(new ErrorMessage(ErrorCode.ADD_COMMENT_ERR_CODE, ErrorCode.ADD_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        try {
            Map<String, Object> equalCondition = new HashMap<String, Object>();
            equalCondition.put(ParamsConstants.PARAM_COMMENT_KTV_ID, comment.getKTVId());
            float ktvScore = ktvService.countScore(commentService.getListEqual(equalCondition));
            KTV ktv = ktvService.get(Integer.valueOf(comment.getKTVId()));
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
        Comment comment = null;

        try {
            comment = commentService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.COUNT_COMMENT_ERR_CODE, ErrorCode.COUNT_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentCount(@QueryParam(ParamsConstants.PARAM_COMMENT_COMMENT) String comment, @QueryParam(ParamsConstants.PARAM_COMMENT_KTV_ID) String ktvId) {
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        int commentCount = 0;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (comment != null
                && !CommonConstants.NULL_STRING.equals(comment)
                && !CommonConstants.NULL.equals(comment))
            likeCondition.put(ParamsConstants.PARAM_COMMENT_COMMENT, comment);
        if (ktvId != null
                && !CommonConstants.NULL_STRING.equals(ktvId)
                && !CommonConstants.NULL.equals(ktvId))
            equalCondition.put(ParamsConstants.PARAM_COMMENT_KTV_ID, ktvId);

        try {
            commentCount = (int) commentService.getListCount(equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.COUNT_COMMENT_ERR_CODE, ErrorCode.COUNT_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        countMap.put(ParamsConstants.PAGE_DATA_COUNT, commentCount);
        return Response.ok(countMap, MediaType.APPLICATION_JSON).build();
    }
}
