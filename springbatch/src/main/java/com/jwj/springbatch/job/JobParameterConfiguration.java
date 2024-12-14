package com.jwj.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

//@Configuration
public class JobParameterConfiguration {

	private JobRepository jobRepository;
	private PlatformTransactionManager transactionManager;

	public JobParameterConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	//@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.next(step2())
				.build();
	}

	//@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						// contribution 사용법
						JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
						jobParameters.getString("name"); // name parameter
						jobParameters.getLong("id"); // id parameter
						jobParameters.getDate("date"); // date parameter
						jobParameters.getDouble("age"); // age parameter

						// chunkcontext 사용법
						Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();


						return RepeatStatus.FINISHED;
					}
				}, transactionManager)
				.build();
	}

	//@Bean
	public Step step2() {
		return new StepBuilder("step2", jobRepository)
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						return RepeatStatus.FINISHED;
					}
				}, transactionManager)
				.build();
	}
}
