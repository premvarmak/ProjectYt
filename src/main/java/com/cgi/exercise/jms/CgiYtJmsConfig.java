package com.cgi.exercise.jms;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CgiYtJmsConfig {
	
	@Bean
	public Queue queueA() {
		return new ActiveMQQueue("cgiyt.queueA");
	}
	
	@Bean
	public Queue queueB() {
		return new ActiveMQQueue("cgiyt.queueB");
	}

}
