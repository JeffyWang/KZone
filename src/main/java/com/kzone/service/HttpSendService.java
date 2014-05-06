package com.kzone.service;

import com.kzone.bo.Response;

import java.io.IOException;

/**
 * Created by Jeffy on 14-5-6.
 */
public interface HttpSendService {
    public byte[] get(String url) throws IOException;

    public byte[] post(String url, Object body);

    public byte[] put(String url, Object body);

    public byte[] delete(String url);
}
