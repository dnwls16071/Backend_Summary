package com.jwj.springbatch.execution;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet2 implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("step2 was executed!");

		ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
		ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

		System.out.println("jobName: " + jobExecutionContext.get("jobName"));
		System.out.println("stepName: " + stepExecutionContext.get("stepName"));
		return RepeatStatus.FINISHED;
	}
}
