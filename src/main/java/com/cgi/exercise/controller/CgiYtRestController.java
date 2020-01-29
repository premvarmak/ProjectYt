package com.cgi.exercise.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.exercise.constants.CgiYtConstants;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.schedulers.Schedulers;

@RestController
@Slf4j
public class CgiYtRestController {
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job cgiYtBatchJob;
	
	@GetMapping(CgiYtConstants.CGI_YT_SEARCH_URL)
	public Map<String, String> launchCgiYtSearchJob(
			@RequestParam(value = CgiYtConstants.QUERY, required = true) String query,
			@RequestParam(value = CgiYtConstants.REPLACE_QUERY, required = true) String replaceQuery,
			@RequestParam(value = CgiYtConstants.MAX_RESULTS, required = true) Long maxResults) {
		Map<String, String> responseMap = new HashMap<>();
		Observable.create(subscriber -> {
			try {
				JobParameters jobParameters = new JobParametersBuilder()
						.addLong(CgiYtConstants.TIME, System.currentTimeMillis())
						.addString(CgiYtConstants.QUERY, query)
						.addString(CgiYtConstants.REPLACE_QUERY, replaceQuery)
						.addLong(CgiYtConstants.MAX_RESULTS, maxResults)
						.toJobParameters();
				jobLauncher.run(cgiYtBatchJob, jobParameters);
			} catch (Exception excep) {
				log.error("Exception while executing CgiYtSearch Job", excep);
			}
		}).subscribeOn(Schedulers.io()).subscribe();
		responseMap.put(CgiYtConstants.STATUS, "CgiYtSeach Job Initiated");
		return responseMap;
	}

}
