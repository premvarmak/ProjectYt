package com.cgi.exercise;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class, 
    DataSourceTransactionManagerAutoConfiguration.class, 
    HibernateJpaAutoConfiguration.class
})
@EnableBatchProcessing
@EnableScheduling
@EnableJms
@ComponentScan
public class CGIExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CGIExerciseApplication.class, args);
	}

}
