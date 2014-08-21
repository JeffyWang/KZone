package com.kzone.rest;

import com.kzone.bean.Comment;
import com.kzone.constants.HTTPConstants;
import com.kzone.constants.ParamsConstants;
import com.wordnik.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jeffy on 2014/8/18 0018.
 */
@Path("/test")
@Api(value = "/test", description = "测试接口")
@Produces({"application/json"})
public class TestRest {

    @GET
    @Path("/info")
    @ApiOperation(value = "测试get", notes = "测试get")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest() {
        return Response.ok().build();
    }

    @PUT
    @Path("/info")
    @ApiOperation(value = "测试put", notes = "测试put")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response putTest(@ApiParam(value = "put string", required = true) String message) {
        System.out.println("test put" + "   " + message);
        return Response.ok().build();
    }

    @POST
    @Path("/info")
    @ApiOperation(value = "测试post", notes = "测试post")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTest( @ApiParam(value = "post data json", required = false) Comment comment) {
        System.out.println("just test post");
        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info")
    @ApiOperation(value = "测试delete", notes = "测试delete")
    @ApiResponses(value = {
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SYS_ERR, message = HTTPConstants.HTTP_CODE_MSG_SYS_ERR),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_NOT_FOUND, message = HTTPConstants.HTTP_CODE_MSG_NOT_FOUND),
            @ApiResponse(code = HTTPConstants.HTTP_CODE_SUCCESS, message = HTTPConstants.HTTP_CODE_MSG_SUCCESS)
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTest() {
        return Response.ok().build();
    }
}
