package com.jwj.springbatch.jojoldu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class StepNextConditionalJobConfiguration {
//
//	private final JobRepository jobRepository;
//	private final PlatformTransactionManager transactionManager;
//
//	@Bean
//	public Job stepNextConditionalJob() {
//		return new JobBuilder("stepNextConditionalJob", jobRepository)
//				.start(conditionalJobStep1())
//					.on("FAILED")		// FAILED 경우
//					.to(conditionalJobStep3())	// step3로 이동
//					.on("*")			// step3 결과와 관계없이
//					.end()						// step3로 이동하면 Flow가 종료
//				.from(conditionalJobStep1())	// step1으로부터
//					.on("*")			// FAILED 외의 모든 경우에 대해
//					.to(conditionalJobStep2())	// step2로 이동
//					.next(conditionalJobStep3())// step2가 종료되면 step3로 이동
//					.on("*")			// step3 결과와 상관없이
//					.end()						// step3로 이동하면 Flow가 종료
//				.end()							// Job 종료
//				.build();
//	}
//
//	@Bean
//	public Step conditionalJobStep1() {
//		return new StepBuilder("step1", jobRepository)
//				.tasklet(((contribution, chunkContext) -> {
//					log.info(">>>>> This is stepNextConditionalJob Step1");
//
//					//contribution.setExitStatus(ExitStatus.FAILED); 주석 처리
//					return RepeatStatus.FINISHED;
//				}), transactionManager)
//				.build();
//	}
//
//	@Bean
//	public Step conditionalJobStep2() {
//		return new StepBuilder("step2", jobRepository)
//				.tasklet(((contribution, chunkContext) -> {
//					log.info(">>>>> This is stepNextConditionalJob Step2");
//					return RepeatStatus.FINISHED;
//				}), transactionManager)
//				.build();
//	}
//
//	@Bean
//	public Step conditionalJobStep3() {
//		return new StepBuilder("step3", jobRepository)
//				.tasklet(((contribution, chunkContext) -> {
//					log.info(">>>>> This is stepNextConditionalJob Step3");
//					return RepeatStatus.FINISHED;
//				}), transactionManager)
//				.build();
//	}
//}
