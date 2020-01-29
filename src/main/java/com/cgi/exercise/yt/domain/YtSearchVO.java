package com.cgi.exercise.yt.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;


@Data
@XmlRootElement(name="video")
@XmlAccessorType(XmlAccessType.FIELD)
public class YtSearchVO {
	@XmlElement(name="url")
	private String ytWatchUrl;
	@XmlElement(name="title")
	private String ytVideoTitle;
	private String ytXmlString;

}
