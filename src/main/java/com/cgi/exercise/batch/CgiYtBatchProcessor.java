package com.cgi.exercise.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.cgi.exercise.jaxb.CgiYtJaxbHelper;
import com.cgi.exercise.yt.domain.YtSearchVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope(value = "step")
public class CgiYtBatchProcessor implements ItemProcessor<YtSearchVO, YtSearchVO> {
	
	@Autowired
	private CgiYtJaxbHelper cgiYtJaxbHelper;
	
	@Override
	public YtSearchVO process(YtSearchVO searchVO) throws Exception {
		searchVO.setYtXmlString(getXmlMessage(searchVO));
		return searchVO;
	}
	
	private String getXmlMessage(YtSearchVO searchVO) {
		String ytXmlMsg = "";
		try {
			ytXmlMsg = cgiYtJaxbHelper.getXmlMessage(searchVO);
			log.info("Xml message to QueueA: {}", ytXmlMsg);
		} catch (Exception e) {
			log.error("Error while creating XML message from SearchVO", e);
		}
		return ytXmlMsg;
	}

}
