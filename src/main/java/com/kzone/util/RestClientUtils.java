package com.kzone.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import com.kzone.constants.HTTPConstants;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;



/**
 * @author daiw
 */
public class RestClientUtils {

	private static final Logger logger = Logger.getLogger(RestClientUtils.class);
	
	/**
	 * Retrieve a representation by doing a GET on the specified URL.
	 * The response (if any) is converted and returned.
	 * <p>URI Template variables are expanded using the given URI variables, if any.
	 * @param timeout the connection timeout setting
	 * @param url the URL
	 * @param headers the headers properties
	 * @param responseType the type of the return value
	 * @return the converted object
	 */
	public static <T> T get(int timeout,String url,Map<String,String> headers, Class<T> responseType, Object... urlVariables) {
		if (!url.toLowerCase().startsWith("http")) {
			url = HTTPConstants.HTTP_PROTOCAL_PATH + url;
		}
		logger.debug("request url:" +url);
		RestTemplate template = new RestTemplate();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		template.setRequestFactory(factory);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAll(headers);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		return template.exchange(url, HttpMethod.GET, requestEntity, responseType,urlVariables).getBody();
	}

	/**
	 * Create a new resource by POSTing the given object to the URI template,
	 * and returns the representation found in the response.
	 * <p>URI Template variables are expanded using the given URI variables, if any.
	 * <p>The {@code request} parameter can be a {@link org.springframework.http.HttpEntity} in order to
	 * add additional HTTP headers to the request.
	 * @param timeout the connection timeout setting
	 * @param url the URL
	 * @param headers the headers properties
	 * @param request the requestBody
	 * @param responseType the type of the return value
	 * @return the converted object
	 */
	public static <T> T post(int timeout,String url, Map<String,String> headers, Object request, Class<T> responseType, Object... urlVariables) {
		if (!url.toLowerCase().startsWith("http")) {
			url = HTTPConstants.HTTP_PROTOCAL_PATH + url;
		}
		logger.debug("request url:" +url);
		RestTemplate template = new RestTemplate();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		template.setRequestFactory(factory);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAll(headers);
		
		HttpEntity<?> requestEntity = new HttpEntity<Object>(request,requestHeaders);
		
//		return template.postForObject(url, requestEntity, responseType);
		return template.exchange(url, HttpMethod.POST, requestEntity, responseType, urlVariables).getBody();
	}

	/**
	 * Delete the resources at the specified URI.
	 * <p>URI Template variables are expanded using the given URI variables, if any.
	 * @param timeout the connection timeout setting
	 * @param url the URL
	 * @param headers the headers properties
	 * @param responseType the type of the return value
	 * @return the converted object
	 */
	public static <T> T delete(int timeout, String url,Map<String,String> headers, Class<T> responseType, Object... urlVariables) {
		if (!url.toLowerCase().startsWith("http")) {
			url = HTTPConstants.HTTP_PROTOCAL_PATH + url;
		}
		logger.debug("request url:" +url);
		if (!url.toLowerCase().startsWith("http")) {
			url = HTTPConstants.HTTP_PROTOCAL_PATH+ url;
		}
		RestTemplate template = new RestTemplate();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		template.setRequestFactory(factory);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAll(headers);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		return template.exchange(url, HttpMethod.DELETE, requestEntity, responseType, urlVariables).getBody();
	}
	/**
	 * Delete the resources at the specified URI.
	 * <p>URI Template variables are expanded using the given URI variables, if any.
	 * @param timeout the connection timeout setting
	 * @param url the URL
	 * @param headers the headers properties
	 * @param request the requestBody
	 * @param responseType the type of the return value
	 * @return the converted object
	 * @throws java.net.URISyntaxException
	 * @throws java.io.IOException
	 */
	public static <T> T delete(int timeout,String url, Map<String,String> headers, Object request, Class<T> responseType, Object... urlVariables) throws IOException, URISyntaxException {
		if (!url.toLowerCase().startsWith("http")) {
			url = HTTPConstants.HTTP_PROTOCAL_PATH + url;
		}
		RestTemplate template = new RestTemplate();
		logger.debug("request url:" +url);
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		template.setRequestFactory(factory);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAll(headers);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(request,requestHeaders);
		return template.exchange(url, HttpMethod.DELETE, requestEntity, responseType, urlVariables).getBody();
	}

	/**
	 * Create or update a resource by PUTting the given object to the URI.
	 * <p>URI Template variables are expanded using the given URI variables, if any.
	 * <p>The {@code request} parameter can be a {@link org.springframework.http.HttpEntity} in order to
	 * add additional HTTP headers to the request.
	 * @param timeout the connection timeout setting
	 * @param url the URL
	 * @param headers the headers properties
	 * @param request the requestBody
	 * @param responseType the type of the return value
	 * @return the converted object
	 */
	public static <T> T put(int timeout,String url, Map<String,String> headers, Object request, Class<T> responseType, Object... urlVariables) {
		if (!url.toLowerCase().startsWith("http")) {
			url = HTTPConstants.HTTP_PROTOCAL_PATH + url;
		}
		logger.debug("request url:" +url);
		RestTemplate template = new RestTemplate();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(timeout);
		template.setRequestFactory(factory);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAll(headers);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(request,requestHeaders);
		return template.exchange(url, HttpMethod.PUT, requestEntity, responseType, urlVariables).getBody();
	}
	
	/**
	 * 
	 * @param request
	 * @param responseType
	 * @param urlVariables
	 * @return
	 */
	/*public static <T> T postFile(MultipartPostMethod request, Class<T> responseType, Object... urlVariables) {
		try{
				HttpClient client = new HttpClient();
			    client.setConnectionTimeout(100000);		       
			    client.executeMethod(request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}*/
}
