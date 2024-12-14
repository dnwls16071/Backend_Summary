package com.jwj.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobExecutionConfiguration {

	private JobRepository jobRepository;
	private PlatformTransactionManager transactionManager;

	public JobExecutionConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	public Job batchJob() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.next(step2())
				.build();
	}

	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						return RepeatStatus.FINISHED;
					}
				}, transactionManager)
				.build();
	}

	@Bean
	public Step step2() {
		return new StepBuilder("step2", jobRepository)
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						throw new RuntimeException("step2 has failed!");
						//return RepeatStatus.FINISHED;
					}
				}, transactionManager)
				.build();
	}
}
