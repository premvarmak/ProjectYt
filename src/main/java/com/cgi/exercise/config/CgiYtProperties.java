package com.cgi.exercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@Component
@Data
public class CgiYtProperties {
	
	@Value("${api.yt.key}")
	private String ytApiKey;
	
	@Value("${api.yt.searchUrl}")
	private String ytSearchUrl;
	
	@Value("${api.yt.watchUrl}")
	private String ytWatchUrl;
	
	@Value("${api.yt.searchType}")
	private String ytSearchType;
	
	@Value("${api.yt.searchPart}")
	private String ytSearchPart;
	
	@Value("${api.yt.maxResults}")
	private Integer maxResults;

}
