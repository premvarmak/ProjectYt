package com.cgi.exercise.rest.core;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CgiYtApi<V> {
	
	private final RestTemplate cgiYtRestTemplate;
	private final String url;
	private final HttpMethod httpMethod;
	private final HttpHeaders httpHeaders;
	private final Object requestBody;
	private final Class<V> responseClass;
	
	public CgiYtApi(RestTemplate cgiYtRestTemplate, String url, HttpMethod httpMethod,
			HttpHeaders httpHeaders, Object requestBody, Class<V> responseClass) {
		this.cgiYtRestTemplate = cgiYtRestTemplate;
		this.url = url;
		this.httpMethod = httpMethod;
		this.httpHeaders = httpHeaders;
		this.requestBody = requestBody;
		this.responseClass = responseClass;
	}
	
	public V run() throws Exception {
		StopWatch sw = new StopWatch();
		sw.start();
		ResponseEntity<V> responseEntity =  cgiYtRestTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestBody,  httpHeaders), responseClass);
		sw.stop();
		log.info("Time taken by "+url+" api is "+sw.getTotalTimeMillis());
		return responseEntity.getBody();
	}

}
