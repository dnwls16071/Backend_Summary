package com.jwj.springbatch.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
//@EnableBatchProcessing(Spring 3.x 버전에서는 필요 X)
public class HelloJobConfiguration {

	//@Bean
	public Job helloJob(JobRepository jobRepository, Step helloStep) {
		return new JobBuilder("helloJob", jobRepository)
				.start(helloStep)
				.build();
	}

	//@Bean
	public Step helloStep(JobRepository jobRepository, Tasklet tasklet, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("helloStep", jobRepository)
				.tasklet(tasklet, platformTransactionManager).build();
	}

	//@Bean
	public Tasklet tasklet() {
		return (((contribution, chunkContext) -> {
			System.out.println(" >> Hello Spring Batch!");
			return RepeatStatus.FINISHED;
		}));
	}
}
