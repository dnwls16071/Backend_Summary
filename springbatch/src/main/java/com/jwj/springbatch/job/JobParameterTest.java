package com.jwj.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Date;

//@Component
public class JobParameterTest implements ApplicationRunner {

	@Autowired private JobLauncher jobLauncher;
	@Autowired private Job job;

	public JobParameterTest(JobLauncher jobLauncher, Job job) {
		this.jobLauncher = jobLauncher;
		this.job = job;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("name", "user1")
				.addLong("id", 2L)
				.addDate("date", new Date())
				.addDouble("age", 16.5)
				.toJobParameters();

		jobLauncher.run(job, jobParameters);
	}
}
