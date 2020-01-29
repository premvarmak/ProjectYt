package com.cgi.exercise.jms;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CgiYtMsgSender {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	public void send(String ytXmlString, Queue queue) {
		try {
			jmsMessagingTemplate.convertAndSend(queue, ytXmlString);
		} catch (Exception excep) {
			log.error("Exception while sending message");
		}
	}

}
