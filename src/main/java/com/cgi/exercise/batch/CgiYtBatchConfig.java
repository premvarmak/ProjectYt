package com.cgi.exercise.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cgi.exercise.constants.CgiYtConstants;
import com.cgi.exercise.yt.domain.YtSearchVO;

@Configuration
public class CgiYtBatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job cgiYtBatchJob(Step sdcBatchStep) {
		return jobBuilderFactory.get("cgiYtBatchJob").incrementer(new RunIdIncrementer()).flow(sdcBatchStep)
				.end().build();
	}
	
	@Bean
	public Step cgiYtBatchStep(CgiYtBatchReader reader, CgiYtBatchProcessor processor, CgiYtBatchWriter writer) {
		return stepBuilderFactory.get("cgiYtBatchStep").<YtSearchVO, YtSearchVO>chunk(10)
				.reader(reader).processor(processor).writer(writer).startLimit(1).build();
	}
	
	@Bean
	@StepScope
	public CgiYtBatchReader cgiYtBatchReader(
			@Value(CgiYtConstants.JOB_PARAM_QUERY) String query,
			@Value(CgiYtConstants.JOB_PARAM_REPLACE_QUERY) String replaceQuery,
			@Value(CgiYtConstants.JOB_PARAM_MAX_RESULTS) Long maxResults) {
		return new CgiYtBatchReader();
	}
	
	@Bean
	@StepScope
	public CgiYtBatchProcessor cgiYtBatchProcessor() {
		return new CgiYtBatchProcessor();
	}
	
	@Bean
	@StepScope
	public CgiYtBatchWriter cgiYtBatchWriter() {
		return new CgiYtBatchWriter();
	}

}
