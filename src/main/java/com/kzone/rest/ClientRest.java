package com.kzone.rest;

import com.kzone.bo.Response;
import com.kzone.service.HttpSendService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by Jeffy on 14-5-6.
 */
@Component
@Path("/client")
public class ClientRest {
    Logger log = Logger.getLogger(ClientRest.class);
    @Autowired
    private HttpSendService httpSendService;

    @GET
    @Path("/http/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        Response response = new Response();
//        String url = "http://localhost:8081";
        String url = "https://localhost";
        String resultBody = "";
        try {
            log.debug("send a request to : [" + url + "]");
            byte[] b = httpSendService.get(url);
            resultBody = new String(b, "UTF-8");
            log.debug("result body : " + resultBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setData(resultBody);
        return response;
    }
}
