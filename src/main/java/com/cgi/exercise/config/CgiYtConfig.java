package com.cgi.exercise.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.cgi.exercise.rest.core.CgiYtApiRequestResponseLoggingInterceptor;

@Configuration
public class CgiYtConfig {

	@Bean(name = "cgiYtRestTemplate")
	public RestTemplate cgiYtRestTemplate() {
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.setInterceptors(Collections.singletonList(new CgiYtApiRequestResponseLoggingInterceptor()));
		return restTemplate;
	}
}
