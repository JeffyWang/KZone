package com.kzone.rest;

import com.kzone.service.HttpSendService;
import com.kzone.service.HttpsSendService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Jeffy on 2014/6/23 0023.
 */
@Component
@Path("/send")
public class HttpSendRest {
    Logger log = Logger.getLogger(HttpSendRest.class);

    @Autowired
    private HttpSendService httpSendService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo() {
        String appkey = "5478786251";
        String secret = "0f3e9dd4e6034113848bf810db360ef3";
        String apiUrl = "http://api.dianping.com/v1/deal/get_all_id_list?";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appkey);
        stringBuilder.append("city上海").append(secret);
        String codes = stringBuilder.toString();
        String sign = DigestUtils.shaHex(codes).toUpperCase();

        apiUrl = apiUrl + "appkey=" + appkey + "&sign=" + sign + "&city=上海";

        byte[] b = null;
        try {
            b = httpSendService.get(apiUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(b, MediaType.APPLICATION_JSON).build();
    }

}
