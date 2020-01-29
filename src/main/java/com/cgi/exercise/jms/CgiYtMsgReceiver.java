package com.cgi.exercise.jms;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.cgi.exercise.jaxb.CgiYtJaxbHelper;
import com.cgi.exercise.yt.domain.YtSearchVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CgiYtMsgReceiver {
	
	@Autowired
	private CgiYtMsgSender cgiYtMsgSender;
	
	@Autowired
	private CgiYtJaxbHelper cgiYtJaxbHelper;
	
	@Setter
	private String searchText;
	
	@Setter
	private String newSearchText;
	
	@Autowired
	private Queue queueB;
	
	@JmsListener(destination = "cgiyt.queueA")
	public void receiveQueue(String ytXmlMsg) {
		YtSearchVO ytSearchVO = cgiYtJaxbHelper.convertXmlMessage(ytXmlMsg);
		ytSearchVO.setYtVideoTitle(ytSearchVO.getYtVideoTitle().replaceAll(searchText, newSearchText));
		String updatedYtXmlMsg = cgiYtJaxbHelper.getXmlMessage(ytSearchVO);
		log.info("Xml message to QueueB: {}", updatedYtXmlMsg);
		cgiYtMsgSender.send(updatedYtXmlMsg, queueB);
	}

}
