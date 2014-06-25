package com.kzone.service.impl;

import com.kzone.bo.Response;
import com.kzone.service.HttpSendService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Jeffy on 14-5-6.
 */
@Service
public class HttpSendServiceImpl implements HttpSendService {
    @Value("#{kzoneConfig['http.request.timeout']}")
    private double httpRequestTimeout;

    @Override
    public byte[] get(String url) throws IOException {
        HttpMethod httpMethod = new GetMethod(url);
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, httpRequestTimeout);
        int statusCode = client.executeMethod(httpMethod);
        byte[] b = httpMethod.getResponseBody();

        return b;
    }

    @Override
    public byte[] post(String url, Object body) throws IOException {
        HttpMethod httpMethod = new PostMethod(url);
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, httpRequestTimeout);
        int statusCode = client.executeMethod(httpMethod);
        byte[] b = httpMethod.getResponseBody();

        return b;
    }

    @Override
    public byte[] put(String url, Object body) throws IOException {
        HttpMethod httpMethod = new PostMethod(url);
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, httpRequestTimeout);
        int statusCode = client.executeMethod(httpMethod);
        byte[] b = httpMethod.getResponseBody();

        return b;
    }

    @Override
    public byte[] delete(String url) throws IOException {
        HttpMethod httpMethod = new DeleteMethod(url);
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, httpRequestTimeout);
        int statusCode = client.executeMethod(httpMethod);
        byte[] b = httpMethod.getResponseBody();

        return b;
    }
}
