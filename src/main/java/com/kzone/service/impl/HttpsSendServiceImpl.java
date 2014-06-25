package com.kzone.service.impl;

import com.kzone.service.HttpsSendService;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

/**
 * Created by Jeffy on 14-5-6.
 */
@Service
public class HttpsSendServiceImpl implements HttpsSendService {
    @Override
    public byte[] get(String url) throws IOException {
        return new byte[0];
    }

//    @Override
//    public byte[] post(String url, Object body) {
//        return new byte[0];
//    }

    @Override
    public byte[] put(String url, Object body) {
        return new byte[0];
    }

    @Override
    public byte[] delete(String url) {
        return new byte[0];
    }

    private KeyStore getKeyStore(String password, String keyStorePath) throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        FileInputStream is = new FileInputStream(keyStorePath);
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }

    private SSLContext getSSLContext(String password, String keyStorePath, String trustStorePath) throws Exception {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore keyStore = getKeyStore(password, keyStorePath);
        keyManagerFactory.init(keyStore, password.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore trustStore = getKeyStore(password, trustStorePath);
        trustManagerFactory.init(trustStore);
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        return ctx;
    }

    public void initHttpsURLConnection(String password, String keyStorePath, String trustStorePath) throws Exception {
        SSLContext sslContext = null;
        HostnameVerifier hnv = new MyHostnameVerifier();
        try {
            sslContext = getSSLContext(password, keyStorePath, trustStorePath);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    }

    public void post(String httpsUrl, String xmlStr) {
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            urlCon.getOutputStream().write(xmlStr.getBytes("utf-8"));
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String password = "password";

        String keyStorePath = "C:\\Users\\Administrator\\Desktop\\1\\tomcat.keystore";

        String trustStorePath = "C:\\Users\\Administrator\\Desktop\\1\\tomcat.keystore";

        String httpsUrl = "https://localhost";

        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fruitShop><fruits><fruit><kind>萝卜</kind></fruit><fruit><kind>菠萝</kind></fruit></fruits></fruitShop>";
        HttpsSendService service = new HttpsSendServiceImpl();
        service.initHttpsURLConnection(password, keyStorePath, trustStorePath);

        service.post(httpsUrl, xmlStr);
    }
}

class MyHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        if("localhost".equals(hostname)){
            return true;
        } else {
            return false;
        }
    }
}
