package com.kzone.rest;

import com.kzone.bean.User;
import com.kzone.bo.Response;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.UserService;
import com.kzone.util.MD5Util;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-7.
 */
@Component
@Path("/account")
public class AccountRest {
    private Logger log = Logger.getLogger(AccountRest.class);
    @Autowired
    private UserService userService;

    @GET
    @Path("/login")
    @Produces("application/json;charset=utf-8")
    public HttpServletRequest loginGetMethod(@QueryParam(ParamsConstants.PARAM_USER_USERNAME)String userName, @QueryParam(ParamsConstants.PARAM_USER_PASSWORD)String password, @Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
        password = MD5Util.string2MD5(password);
        log.debug("login user name : [" + userName + "]" + ", password with md5 : [" + password + "]");

        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.PARAM_USER_USERNAME,userName);
        User user = new User();
        try {
            user = userService.getUser(equalCondition);
            log.debug("get user [" + user.getName() + "], and password is : [" + userService.decryption(user.getPassword()) +"]");
        } catch (Exception e) {
            log.warn(e);
        }

        if(password.equals(userService.decryption(user.getPassword()))) {
            request.getSession().setAttribute(ParamsConstants.PARAM_USER_USERNAME, userName);
        } else {
            response.sendError(HttpStatus.SC_BAD_REQUEST,ErrorCode.LOGIN_ERR_MSG);
        }

        return request;
    }

    @GET
    @Path("/logout")
    @Produces("application/json;charset=utf-8")
    public HttpServletRequest logoutGetMethod(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        String userName = (String) request.getSession().getAttribute(ParamsConstants.PARAM_USER_USERNAME);
        log.debug("logout user name : [" + userName + "]");
        String loginName = (String) request.getSession().getAttribute(ParamsConstants.PARAM_USER_USERNAME);

        if(userName.equals(loginName)) {
            request.getSession().removeAttribute(ParamsConstants.PARAM_USER_USERNAME);
        }

        return request;
    }

    @POST
    @Path("/login")
    @Produces("application/json;charset=utf-8")
    public void loginPostMethod(@QueryParam(ParamsConstants.PARAM_USER_USERNAME)String userName, @QueryParam(ParamsConstants.PARAM_USER_PASSWORD)String password, @Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
        loginGetMethod(userName, password, request, response);
    }

    @POST
    @Path("/logout")
    @Produces("application/json;charset=utf-8")
    public void logoutPostMethod(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        logoutGetMethod(request, response);
    }
}