package com.kzone.service;

import java.io.IOException;

/**
 * Created by Jeffy on 14-5-6.
 */
public interface HttpsSendService {
    public byte[] get(String url) throws IOException;

//    public byte[] post(String url, Object body);

    public byte[] put(String url, Object body);

    public byte[] delete(String url);

    void initHttpsURLConnection(String password, String keyStorePath, String trustStorePath) throws Exception;

    void post(String httpsUrl, String xmlStr);
}
