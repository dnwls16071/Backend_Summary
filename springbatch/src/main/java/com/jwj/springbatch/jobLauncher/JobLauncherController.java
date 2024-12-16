package com.jwj.springbatch.jobLauncher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

//@RestController
//public class JobLauncherController {
//
//	private final Job job;
//	private final JobLauncher jobLauncher;
//	private final JobLauncher asyncJobLauncher;
//
//	public JobLauncherController(Job job,
//								 JobLauncher jobLauncher,
//								 @Qualifier("asyncJobLauncher") JobLauncher asyncJobLauncher) {
//		this.job = job;
//		this.jobLauncher = jobLauncher;
//		this.asyncJobLauncher = asyncJobLauncher;
//	}
//
//	@PostMapping("/batchSync")
//	public String launch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//		JobParameters jobParameters = new JobParametersBuilder()
//				.addString("id", member.getId())
//				.addDate("date", new Date())
//				.toJobParameters();
//		jobLauncher.run(job, jobParameters);
//		return "batch completed!";
//	}
//
//	@PostMapping("/batchAsync")
//	public String launchAsync(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//		JobParameters jobParameters = new JobParametersBuilder()
//				.addString("id", member.getId())
//				.addDate("date", new Date())
//				.toJobParameters();
//		asyncJobLauncher.run(job, jobParameters);
//		return "batch launched asynchronously!";
//	}
//}
