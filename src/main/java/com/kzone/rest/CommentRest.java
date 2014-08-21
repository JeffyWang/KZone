package com.kzone.rest;

import com.kzone.bean.Comment;
import com.kzone.bean.KTV;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.HTTPConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.CommentService;
import com.kzone.service.KTVService;
import com.kzone.util.StringUtil;
import com.wordnik.swagger.annotations.*;
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
@Api(value = "/comment", description = "评论相关接口")
public class CommentRest {
    Logger log = Logger.getLogger(CommentRest.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    KTVService ktvService;

    @GET
    @Path("/info/{id}")
    @ApiOperation(value = "通过评论id查询评论详细信息", notes = "传入一个评论id，返回评论的详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(
            @ApiParam(value = "评论id", required = true)
            @PathParam("id") int id) {
        Comment comment = null;

        try {
            comment = commentService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_COMMENT_ERR_CODE, ErrorCode.GET_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get a " + comment.toString());
        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @ApiOperation(value = "查询评论信息列表", notes = "传入参数，返回评论信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsPage(
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_OFFSET_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_OFFSET) int offset,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_LENGTH_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_LENGTH) int length,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_ORDER_DESC_MSG, required = false)
            @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc,
//            @ApiParam(value = ParamsConstants.PARAM_COMMENT_SCORE_MSG, required = false)
//            @QueryParam(ParamsConstants.PARAM_COMMENT_SCORE) String score,
            @ApiParam(value = ParamsConstants.PARAM_COMMENT_COMMENT_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_COMMENT_COMMENT) String comment,
            @ApiParam(value = ParamsConstants.PARAM_COMMENT_KTV_ID_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_COMMENT_KTV_ID) String ktvId) {
        List<Comment> commentList = null;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();
        Map<String, String> gtCondition = new HashMap<String, String>();

//        if (score != null
//                && !CommonConstants.NULL_STRING.equals(score)
//                && !CommonConstants.NULL.equals(score))
//            gtCondition.put(ParamsConstants.PARAM_COMMENT_SCORE,score);
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

        log.debug("Get comments pages success.");
        return Response.ok(commentList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info/page")
    @ApiOperation(value = "查询评论信息列表", notes = "传入参数，返回评论信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsPages(
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_PAGE_NUM_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_PAGE_NUM) int pageNum,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_PAGE_DATA_COUNT_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_PAGE_DATA_COUNT) int pageDataCount,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_ORDER_DESC_MSG, required = false)
            @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc,
//            @ApiParam(value = ParamsConstants.PARAM_COMMENT_SCORE_MSG, required = false)
//            @QueryParam(ParamsConstants.PARAM_COMMENT_SCORE) String score,
            @ApiParam(value = ParamsConstants.PARAM_COMMENT_COMMENT_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_COMMENT_COMMENT) String comment,
            @ApiParam(value = ParamsConstants.PARAM_COMMENT_KTV_ID_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_COMMENT_KTV_ID) String ktvId) {
        List<Comment> commentList = null;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();
        Map<String, String> gtCondition = new HashMap<String, String>();
//
//        if (score != null
//                && !CommonConstants.NULL_STRING.equals(score)
//                && !CommonConstants.NULL.equals(score))
//            gtCondition.put(ParamsConstants.PARAM_COMMENT_SCORE,score);
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
            long dataCount = commentService.getListCount(equalCondition, likeCondition);
            int pageCount = 0;

            if(dataCount % pageDataCount == 0)
                pageCount = (int) (dataCount / pageDataCount);
            else
                pageCount = (int) (dataCount / pageDataCount) + 1;

            int offset = pageNum * pageDataCount;
            int length = pageDataCount;

            commentList = commentService.getListForPage(Comment.class, offset, length, orderDesc, equalCondition, likeCondition, gtCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_COMMENT_LIST_ERR_CODE, ErrorCode.GET_COMMENT_LIST_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get comments pages success.");
        return Response.ok(commentList, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @ApiOperation(value = "发表评论", notes = "发表评论")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(
            @ApiParam(value = "评论内容json", required = true)
            Comment comment) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        try {
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

        log.debug("Add a new " + comment.toString());
        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(
            @PathParam("id") int id) {
        Comment comment = null;

        try {
            comment = commentService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.COUNT_COMMENT_ERR_CODE, ErrorCode.COUNT_COMMENT_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Delete a " + comment.toString());
        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/count")
    @ApiOperation(value = "查询评论总数", notes = "查询评论总数")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentCount(
//            @ApiParam(value = ParamsConstants.PARAM_COMMENT_SCORE_MSG, required = false)
//            @QueryParam(ParamsConstants.PARAM_COMMENT_SCORE) String score,
            @ApiParam(value = ParamsConstants.PARAM_COMMENT_COMMENT_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_COMMENT_COMMENT) String comment,
            @ApiParam(value = ParamsConstants.PARAM_COMMENT_KTV_ID_MSG, required = false)
            @QueryParam(ParamsConstants.PARAM_COMMENT_KTV_ID) String ktvId) {
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        int commentCount = 0;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();
//        Map<String, String> gtCondition = new HashMap<String, String>();
//
//        if (score != null
//                && !CommonConstants.NULL_STRING.equals(score)
//                && !CommonConstants.NULL.equals(score))
//            gtCondition.put(ParamsConstants.PARAM_COMMENT_SCORE,score);

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
        log.debug("Get the comments' count is {" + commentCount + "}");
        return Response.ok(countMap, MediaType.APPLICATION_JSON).build();
    }
}
