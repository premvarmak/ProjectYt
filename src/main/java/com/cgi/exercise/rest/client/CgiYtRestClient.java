package com.cgi.exercise.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cgi.exercise.config.CgiYtProperties;
import com.cgi.exercise.constants.CgiYtConstants;
import com.cgi.exercise.rest.core.CgiYtApi;
import com.cgi.exercise.yt.domain.YtSearchItem;
import com.cgi.exercise.yt.domain.YtSearchResponse;
import com.cgi.exercise.yt.domain.YtSearchVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CgiYtRestClient {
	
	@Autowired
	private CgiYtProperties cgiYtProps;
	@Autowired
	private RestTemplate cgiYtRestTemplate;
	
	public YtSearchResponse getYtSearchResults(String q, Long maxResults) throws Exception {
		CgiYtApi<YtSearchResponse> api = new CgiYtApi<YtSearchResponse>(cgiYtRestTemplate, getYtSearchUrl(q, maxResults), HttpMethod.GET,
				getYtHeaders(), null, YtSearchResponse.class);
		return api.run();
	}
	
	public YtSearchVO getYtSearchVO(YtSearchItem ytSearchItem) {
		YtSearchVO searchVO = new YtSearchVO();
		try {
			searchVO.setYtWatchUrl(getYtWatchUrl(ytSearchItem.getId().getVideoId()));
			searchVO.setYtVideoTitle(ytSearchItem.getSnippet().getTitle());
		} catch (Exception excep) {
			log.error("Exception while processing response of Youtube search API", excep);
			searchVO.setYtVideoTitle("");
			searchVO.setYtWatchUrl("");
		}
		return searchVO;
	}
	
	private String getYtWatchUrl(String videoId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(cgiYtProps.getYtWatchUrl())
		        .queryParam(CgiYtConstants.V, videoId);
		return builder.build().toString();
	}
	
	private String getYtSearchUrl(String q, Long maxResults) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(cgiYtProps.getYtSearchUrl())
		        .queryParam(CgiYtConstants.PART, cgiYtProps.getYtSearchPart())
		        .queryParam(CgiYtConstants.MAX_RESULTS, maxResults)
		        .queryParam(CgiYtConstants.Q, q).queryParam(CgiYtConstants.TYPE, cgiYtProps.getYtSearchType())
		        .queryParam(CgiYtConstants.KEY, cgiYtProps.getYtApiKey());
		return builder.build().toString();
	}
	private HttpHeaders getYtHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
		return headers;
	}

}
