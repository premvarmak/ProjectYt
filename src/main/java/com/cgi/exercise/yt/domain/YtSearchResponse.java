package com.cgi.exercise.yt.domain;

import java.util.List;

import lombok.Data;

@Data
public class YtSearchResponse {
	
	private String kind;
	private String etag;
	private String regionCode;
	private YtPageInfo pageInfo;
	private List<YtSearchItem> items;

}
