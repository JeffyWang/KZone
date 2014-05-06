package com.kzone.service.impl;

import com.kzone.bo.Response;
import com.kzone.service.HttpSendService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Jeffy on 14-5-6.
 */
@Service
public class HttpSendServiceImpl implements HttpSendService {
    @Override
    public byte[] get(String url) throws IOException {
        HttpMethod httpMethod = new GetMethod(url);
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 100000);
        int statusCode = client.executeMethod(httpMethod);
        byte[] b = httpMethod.getResponseBody();

        return b;
    }

    @Override
    public byte[] post(String url, Object body) {
        return null;
    }

    @Override
    public byte[] put(String url, Object body) {
        return null;
    }

    @Override
    public byte[] delete(String url) {
        return null;
    }
}
