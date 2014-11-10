package com.kzone.rest;

import com.kzone.bean.User;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.HTTPConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.UserService;
import com.kzone.util.StringUtil;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * Created by Jeffy on 14-4-24.
 */
@Component
@Path("/user")
@Api(value = "/user", description = "用户相关接口")
public class UserRest {
    Logger log = Logger.getLogger(UserRest.class);
    @Autowired
    private UserService userService;

    @GET
    @Path("/info/{id}")
    @ApiOperation(value = "通过用户id查询用户详细信息", notes = "传入一个用户id，返回用户的详细信息")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(
            @ApiParam(value = "用户id", required = true)
            @PathParam("id") String id) {
        User user = null;

        try {
            if(userService.getUser(id) != null){
                user = userService.getUser(id);
            } else {
                user = userService.getUser(Integer.valueOf(id));
            }
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_USER_ERR_CODE, ErrorCode.GET_USER_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Get a " + user.toString());
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }

//    @GET
//    @Path("/info")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUsers() {
//        List<User> userList = null;
//
//        try {
//            userList = userService.getUsers(null, null);
//        } catch (Exception e) {
//            log.warn(e);
//            return Response.ok(new ErrorMessage(ErrorCode.GET_USER_LIST_ERR_CODE, ErrorCode.GET_USER_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
//        }
//
//        log.debug("Get user list success.");
//        return Response.ok(userList, MediaType.APPLICATION_JSON).build();
//    }

    @GET
    @Path("/info")
    @ApiOperation(value = "查询用户信息列表", notes = "传入参数，返回用户信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersPage(
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_OFFSET_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_OFFSET) int offset,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_LENGTH_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_LENGTH) int length,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_ORDER_DESC_MSG, required = false)
            @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDes) {
        List<User> usersPage = null;

        Map<String, String> equalCondition = new HashMap<String, String>();
        Map<String, String> likeCondition = new HashMap<String, String>();

        try {
            usersPage = userService.getUsersPage(offset, length, orderDes, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_USER_LIST_ERR_CODE, ErrorCode.GET_USER_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Get users pages success");
        return Response.ok(usersPage, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @ApiOperation(value = "查询用户信息列表", notes = "传入参数，返回用户信息列表")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersPages(
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_PAGE_NUM_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_PAGE_NUM) int pageNum,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_PAGE_DATA_COUNT_MSG, required = true)
            @QueryParam(ParamsConstants.PAGE_PARAMS_PAGE_DATA_COUNT) int pageDataCount,
            @ApiParam(value = ParamsConstants.PAGE_PARAMS_ORDER_DESC_MSG, required = false)
            @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc) {
        List<User> usersPage = null;

        Map<String, String> equalCondition = new HashMap<String, String>();
        Map<String, String> likeCondition = new HashMap<String, String>();

        try {
            long dataCount = userService.getListCount(equalCondition, likeCondition);
            int pageCount = 0;

            if(dataCount % pageDataCount == 0)
                pageCount = (int) (dataCount / pageDataCount);
            else
                pageCount = (int) (dataCount / pageDataCount) + 1;

            int offset = pageNum * pageDataCount;
            int length = pageDataCount;

            usersPage = userService.getListForPage(User.class, offset, length, orderDesc, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_USER_LIST_ERR_CODE, ErrorCode.GET_USER_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Get users pages success");
        return Response.ok(usersPage, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(
            @ApiParam(value = "新增用户信息json", required = true)
            User user) {
        UUID uuid = UUID.randomUUID();

        try {
            user.setUuid(uuid.toString());
            user.setPassword(userService.encryption(user.getPassword()));
            user = userService.addUser(user);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_USER_ERR_CODE, ErrorCode.ADD_USER_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Add a " + user.toString());
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }

    public Response validateUser(User user, Response response) throws Exception {
        Validate.notNull(user, "The user is not exist");
//        Validate.notNull(user.getName(), "The username can't be null");
        Validate.notNull(user.getPassword(), "The password can't be null");
//        Validate.isTrue(StringUtils.isNotBlank(user.getName()), "The username can't be ''");
//        Validate.isTrue(!userService.isExist(user.getName()), "The username [" + user.getName() + "] is exist");
        return response;
    }
}
