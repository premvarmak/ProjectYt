package com.cgi.exercise.batch;


import java.util.List;

import javax.jms.Queue;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

import com.cgi.exercise.jms.CgiYtMsgSender;
import com.cgi.exercise.yt.domain.YtSearchVO;

@Scope(value = "step")
public class CgiYtBatchWriter implements ItemWriter<YtSearchVO> {
	
	@Autowired
	private Queue queueA;
	
	@Autowired
	private CgiYtMsgSender cgiYtMsgSender;
	
	@Override
	public void write(List<? extends YtSearchVO> ytSearchVOs) throws Exception {
		if (!CollectionUtils.isEmpty(ytSearchVOs)) {
			ytSearchVOs.forEach(searchVO -> cgiYtMsgSender.send(searchVO.getYtXmlString(), queueA));
		}
	}

}
