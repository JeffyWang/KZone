package com.kzone.rest;

import com.kzone.bean.User;
import com.kzone.bo.Response;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.UserService;
import com.kzone.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.*;

/**
 * Created by Jeffy on 14-4-24.
 */
@Component
@Path("/user")
public class UserRest {
    Logger log = Logger.getLogger(UserRest.class);
    @Autowired
    private UserService userService;

    @GET
    @Path("/info/{id}")
    @Produces("application/json;charset=utf-8")
    public Response getUser(@PathParam("id") String id) {
        Response response = new Response();
        User user = null;

        try {
            if(userService.getUser(id) != null){
                user = userService.getUser(id);
            } else {
                user = userService.getUser(Integer.valueOf(id));
            }
//            response = validateUser(user, response);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_USER_ERR_CODE, ErrorCode.GET_USER_ERR_MSG + e.getMessage());
        }

        response.setData(user);
        return response;
    }

    @GET
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response getUsers() {
        Response response = new Response();
        List<User> users = new ArrayList<User>();

        try {
            users = userService.getUsers(null, null);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_USER_LIST_ERR_CODE, ErrorCode.GET_USER_LIST_ERR_MSG + e.getMessage());
        }

        response.setData(users);
        return response;
    }

    @GET
    @Path("/info/{offset}/{length}/{equalParams}/{likePrams}")
    @Produces("application/json;charset=utf-8")
    public Response getUsersPage(@Context UriInfo uriInfo) {
        Response response = new Response();
        List<User> usersPage = null;

        MultivaluedMap<String, String> params = uriInfo.getPathParameters();
        Map<String, String> equalCondition = new HashMap<String, String>();
        Map<String, String> likeCondition = new HashMap<String, String>();
        int offset = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_OFFSET));
        int length = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_LENGTH));

        System.out.println(offset + "   " + length);
        // 精确查询条件健值对
        if (params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS)))
            equalCondition.put(ParamsConstants.PARAM_ID, params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS));
        // 模糊查询条件健值对
        if (params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS)))
            likeCondition.put(ParamsConstants.PARAM_UUID, params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS));

        try {
            usersPage = userService.getUsersPage(offset, length, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.GET_USER_LIST_ERR_CODE, ErrorCode.GET_USER_LIST_ERR_MSG + e.getMessage());
        }

        response.setData(usersPage);
        return response;
    }

    @POST
    @Path("/info")
    @Produces("application/json;charset=utf-8")
    public Response addUser(@RequestBody String body) {
        Response response = new Response();
        User user = null;
        UUID uuid = UUID.randomUUID();

        try {
            user = StringUtil.jsonStringToObject(body, User.class);
//            user.setUuid(uuid.toString());
//            response = validateUser(user, response);
//            user.setPassword(userService.encryption(user.getPassword()));
//            user = userService.addUser(user);
        } catch (Exception e) {
            log.warn(e);
            return response.setResponse(ErrorCode.ADD_USER_ERR_CODE, ErrorCode.ADD_USER_ERR_MSG + e.getMessage());
        }

        response.setData(user);
        return response;
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
