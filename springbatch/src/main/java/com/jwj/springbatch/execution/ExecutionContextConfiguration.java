package com.jwj.springbatch.execution;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
public class ExecutionContextConfiguration {

	private JobRepository jobRepository;
	private PlatformTransactionManager transactionManager;
	private ExecutionContextTasklet1 tasklet1;
	private ExecutionContextTasklet2 tasklet2;
	private ExecutionContextTasklet3 tasklet3;
	private ExecutionContextTasklet4 tasklet4;

	public ExecutionContextConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager, ExecutionContextTasklet1 tasklet1, ExecutionContextTasklet2 tasklet2, ExecutionContextTasklet3 tasklet3, ExecutionContextTasklet4 tasklet4) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.tasklet1 = tasklet1;
		this.tasklet2 = tasklet2;
		this.tasklet3 = tasklet3;
		this.tasklet4 = tasklet4;
	}

	//@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.next(step2())
				.next(step3())
			    .next(step4())
				.build();
	}

	//@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.tasklet(tasklet1, transactionManager)
				.build();
	}

	//@Bean
	public Step step2() {
		return new StepBuilder("step2", jobRepository)
				.tasklet(tasklet2, transactionManager)
				.build();
	}

	//@Bean
	public Step step3() {
		return new StepBuilder("step3", jobRepository)
				.tasklet(tasklet3, transactionManager)
				.build();
	}

	//@Bean
	public Step step4() {
		return new StepBuilder("step4", jobRepository)
				.tasklet(tasklet4, transactionManager)
				.build();
	}
}
