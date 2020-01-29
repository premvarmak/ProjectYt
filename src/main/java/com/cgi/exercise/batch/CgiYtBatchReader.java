package com.cgi.exercise.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

import com.cgi.exercise.constants.CgiYtConstants;
import com.cgi.exercise.jms.CgiYtMsgReceiver;
import com.cgi.exercise.rest.client.CgiYtRestClient;
import com.cgi.exercise.yt.domain.YtSearchResponse;
import com.cgi.exercise.yt.domain.YtSearchVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope(value = "step")
public class CgiYtBatchReader implements ItemReader<YtSearchVO> {
	
	@Autowired
	private CgiYtRestClient cgiYtRestClient;
	
	@Autowired
	private CgiYtMsgReceiver cgiYtMsgReceiver;
	
	@Value(CgiYtConstants.JOB_PARAM_QUERY)
	private String query;
	
	@Value(CgiYtConstants.JOB_PARAM_REPLACE_QUERY)
	private String replaceQuery;
	
	@Value(CgiYtConstants.JOB_PARAM_MAX_RESULTS)
	private Long maxResults;
	
	private Iterator<YtSearchVO> searchVOIterator = null;

	@BeforeStep
	public void beforeStep() {
		try {
			init();
			YtSearchResponse ytResp = cgiYtRestClient.getYtSearchResults(query, maxResults);
			List<YtSearchVO> searchVOList = prepareSearchVOs(ytResp);
			if (!CollectionUtils.isEmpty(searchVOList))
				searchVOIterator = searchVOList.iterator();
		} catch (Exception e) {
			log.error("Exception while fecthing Youtube search results", e);
		}
	}

	private void init() {
		cgiYtMsgReceiver.setSearchText("(?i)"+query);
		cgiYtMsgReceiver.setNewSearchText(replaceQuery);
	}

	@Override
	public YtSearchVO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		YtSearchVO searchVO = null;
		if (null != searchVOIterator && searchVOIterator.hasNext()) {
			searchVO = searchVOIterator.next();
			searchVOIterator.remove();
		}
		return searchVO;
	}
	
	@AfterStep
	public void afterStep() {
		log.info("CgiYtSearch Job execution completed!!!");
	}
	
	private List<YtSearchVO> prepareSearchVOs(YtSearchResponse ytResp) {
		List<YtSearchVO> searchVOList = new ArrayList<>();
		ytResp.getItems().forEach(ytItem -> {
			searchVOList.add(cgiYtRestClient.getYtSearchVO(ytItem));
		});
		return searchVOList;
	}

}
