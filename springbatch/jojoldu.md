
### ✅ Batch 구조

<img src="https://github.com/jojoldu/spring-batch-in-action/raw/master/images/2/jobstep.png">

### ✅ MySQL에서 Spring Batch 실행과 그 과정에서 나타나는 메타데이터 테이블 알아보기

<img src="https://github.com/jojoldu/spring-batch-in-action/raw/master/images/2/domain1.png">

> **BATCH_JOB_INSTANCE**
* `JOB_INSTANCE_ID` : PK
* `JOB_NAME` : 수행한 Batch Job Name
* `JOB_PARAMETER` : Spring Batch가 실행될 때 외부에서 받을 수 있는 파라미터로 만약 다르다면 기록이 되고 같다면 기록이 되지 않는다.

> **BATCH_JOB_EXECUTION**
* 하나의 BATCH_JOB_INSTANCE에 대해 여러 BATCH_JOB_EXECUTION을 가질 수 있다.(1 : N)

<img src="https://github.com/jojoldu/spring-batch-in-action/raw/master/images/3/jobschema.png">

> **BATCH_JOB_EXECUTION_PARAMS**
* BATCH_JOB_EXECUTION 테이블이 생성될 당시에 입력 받은 Job Parameters를 담고 있다.

### ✅ Spring Batch Job Flow

> **Step**
* `Step`은 실제 Batch 작업을 수행하는 역할을 한다.
* 실제로 Batch 비즈니스 로직을 처리하는 기능은 Step에 구현된다.
* 이처럼 Step에서는 Batch로 실제 처리하고자 하는 기능과 설정을 모두 포함하는 단계라고 생각하면 된다.

### ✅ Next

> **next()**
* 순차적으로 Step을 연결시킬 때 사용된다.

<img src="https://github.com/jojoldu/spring-batch-in-action/raw/master/images/4/conditional1.png">

* `next()`는 위와 같이 Step의 순서를 제어할 수 있다.
* 만약 앞의 Step에서 오류가 발생하면 뒤의 Step들이 실행되지 않는다.
* 하지만 상황에 따라 정상일 때는 Step B로, 오류가 발생했을 때는 Step C로 수행해야 할 때가 있다.
* 이런 경우에 대비해 Spring Batch Job에서는 조건별로 Step을 사용할 수 있다.
* 코드 시나리오는 아래와 같다.

> Step1 실패 시나리오 : Step1 -> Step3<br>
> Step1 성공 시나리오 : Step1 -> Step2 -> Step3

👉관련 코드

```java
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextConditionalJobConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	@Bean
	public Job stepNextConditionalJob() {
		return new JobBuilder("stepNextConditionalJob", jobRepository)
				.start(conditionalJobStep1())
					.on("FAILED")		// FAILED 경우
					.to(conditionalJobStep3())	// step3로 이동
					.on("*")			// step3 결과와 관계없이
					.end()						// step3로 이동하면 Flow가 종료
				.from(conditionalJobStep1())	// step1으로부터
					.on("*")			// FAILED 외의 모든 경우에 대해
					.to(conditionalJobStep2())	// step2로 이동
					.next(conditionalJobStep3())// step2가 종료되면 step3로 이동
					.on("*")			// step3 결과와 상관없이
					.end()						// step3로 이동하면 Flow가 종료
				.end()							// Job 종료
				.build();
	}

	@Bean
	public Step conditionalJobStep1() {
		return new StepBuilder("step1", jobRepository)
				.tasklet(((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step1");

					contribution.setExitStatus(ExitStatus.FAILED);
					return RepeatStatus.FINISHED;
				}), transactionManager)
				.build();
	}

	@Bean
	public Step conditionalJobStep2() {
		return new StepBuilder("step2", jobRepository)
				.tasklet(((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step2");
					return RepeatStatus.FINISHED;
				}), transactionManager)
				.build();
	}

	@Bean
	public Step conditionalJobStep3() {
		return new StepBuilder("step3", jobRepository)
				.tasklet(((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step3");
					return RepeatStatus.FINISHED;
				}), transactionManager)
				.build();
	}
}
```

* 현재 Step1에서 실패로 귀결이 되는 상황이다. 이렇게 되었을 경우 현재 원하는 시나리오는 Step1이 실패했기에 Step3로 가는 것이다.
* 결과를 확인해보면 Step1과 Step3만 실행되는 것을 볼 수 있다.

👉실행 결과

```text
2024-12-16T23:19:32.759+09:00  INFO 78850 --- [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [FlowJob: [name=stepNextConditionalJob]] launched with the following parameters: [{}]
2024-12-16T23:19:32.783+09:00  INFO 78850 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2024-12-16T23:19:32.790+09:00  INFO 78850 --- [           main] .s.j.StepNextConditionalJobConfiguration : >>>>> This is stepNextConditionalJob Step1
2024-12-16T23:19:32.794+09:00  INFO 78850 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 10ms
2024-12-16T23:19:32.810+09:00  INFO 78850 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step3]
2024-12-16T23:19:32.815+09:00  INFO 78850 --- [           main] .s.j.StepNextConditionalJobConfiguration : >>>>> This is stepNextConditionalJob Step3
2024-12-16T23:19:32.818+09:00  INFO 78850 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step3] executed in 7ms
2024-12-16T23:19:32.826+09:00  INFO 78850 --- [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [FlowJob: [name=stepNextConditionalJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 59ms
```

* 그렇다면 이제 위의 코드를 수정해서 Step1 -> Step2 -> Step3가 실행이 되도록 확인해보자.

👉관련 코드

```java
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextConditionalJobConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	@Bean
	public Job stepNextConditionalJob() {
		return new JobBuilder("stepNextConditionalJob", jobRepository)
				.start(conditionalJobStep1())
					.on("FAILED")		// FAILED 경우
					.to(conditionalJobStep3())	// step3로 이동
					.on("*")			// step3 결과와 관계없이
					.end()						// step3로 이동하면 Flow가 종료
				.from(conditionalJobStep1())	// step1으로부터
					.on("*")			// FAILED 외의 모든 경우에 대해
					.to(conditionalJobStep2())	// step2로 이동
					.next(conditionalJobStep3())// step2가 종료되면 step3로 이동
					.on("*")			// step3 결과와 상관없이
					.end()						// step3로 이동하면 Flow가 종료
				.end()							// Job 종료
				.build();
	}

	@Bean
	public Step conditionalJobStep1() {
		return new StepBuilder("step1", jobRepository)
				.tasklet(((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step1");

					//contribution.setExitStatus(ExitStatus.FAILED); 주석 처리
					return RepeatStatus.FINISHED;
				}), transactionManager)
				.build();
	}

	@Bean
	public Step conditionalJobStep2() {
		return new StepBuilder("step2", jobRepository)
				.tasklet(((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step2");
					return RepeatStatus.FINISHED;
				}), transactionManager)
				.build();
	}

	@Bean
	public Step conditionalJobStep3() {
		return new StepBuilder("step3", jobRepository)
				.tasklet(((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step3");
					return RepeatStatus.FINISHED;
				}), transactionManager)
				.build();
	}
}
```

👉실행 결과

```text
2024-12-16T23:22:58.238+09:00  INFO 79198 --- [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [FlowJob: [name=stepNextConditionalJob]] launched with the following parameters: [{}]
2024-12-16T23:22:58.260+09:00  INFO 79198 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2024-12-16T23:22:58.268+09:00  INFO 79198 --- [           main] .s.j.StepNextConditionalJobConfiguration : >>>>> This is stepNextConditionalJob Step1
2024-12-16T23:22:58.272+09:00  INFO 79198 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 11ms
2024-12-16T23:22:58.288+09:00  INFO 79198 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
2024-12-16T23:22:58.293+09:00  INFO 79198 --- [           main] .s.j.StepNextConditionalJobConfiguration : >>>>> This is stepNextConditionalJob Step2
2024-12-16T23:22:58.295+09:00  INFO 79198 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step2] executed in 7ms
2024-12-16T23:22:58.309+09:00  INFO 79198 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step3]
2024-12-16T23:22:58.315+09:00  INFO 79198 --- [           main] .s.j.StepNextConditionalJobConfiguration : >>>>> This is stepNextConditionalJob Step3
2024-12-16T23:22:58.318+09:00  INFO 79198 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step3] executed in 8ms
2024-12-16T23:22:58.326+09:00  INFO 79198 --- [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [FlowJob: [name=stepNextConditionalJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 81ms
```

* 위에서 나온 조건별 흐름 제어를 설명할 때 언급됐지만 BatchStatus와 ExitStatus의 차이를 아는 것이 중요하다.
* BatchStatus는 Job 또는 Step의 실행 결과를 Spring에서 기록할 때 사용하는 Enum이다.
* BatchStatus로 사용되는 값에는 COMPLETED, STARTING, STARTED, STOPPING, FAILED, ABANDONED, UNKNOWN 등이 있다.

<img src="/jojoldu/batchstatus.png">

* `ExitStatus`는 Step의 실행 후 상태를 의미한다.

<img src="/jojoldu/exitStatus.png">

### ✅ Decide

* 위에서 설명한 Step의 결과에 따른 처리에서 서로 다른 Step으로 이동할 수 있다는 것을 알게 되었다.
* 하지만 이 방식은 2가지 문제가 있다.
  * Step이 담당하는 역할이 2개 이상이 된다.
  * 다양한 분기 로직 처리의 어려움

👉관련 코드

```java
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeciderJobConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	@Bean
	public Job stepNextConditionalJob() {
		return new JobBuilder("deciderJob", jobRepository)
				.start(startStep())
				.next(decider()) 	// 홀수 | 짝수 구분
				.from(decider()) 	// decider의 상태가
					.on("ODD") 		// ODD라면
					.to(oddStep()) 	// oddStep로 간다.
				.from(decider()) 	// decider의 상태가
					.on("EVEN") 	// ODD라면
					.to(evenStep()) // evenStep로 간다.
				.end() 				// builder 종료
				.build();
	}

	@Bean
	public Step startStep() {
		return new StepBuilder("startStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>> startStep was executed!");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
	}

	@Bean
	public Step evenStep() {
		return new StepBuilder("evenStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
					log.info(">>>>> 짝수입니다.");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
	}

	@Bean
	public Step oddStep() {
		return new StepBuilder("oddStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>> 홀수입니다.");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
	}

	@Bean
	public JobExecutionDecider decider() {
		return new OddDecider();
	}

	public static class OddDecider implements JobExecutionDecider {

		@Override
		public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
			Random random = new Random();

			int randomNumber = random.nextInt(50) + 1;
			log.info("random number : " + randomNumber);

			if (randomNumber % 2 == 0) {
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
		}
	}
}
```

👉실행 결과

```text
2024-12-16T23:36:56.613+09:00  INFO 80542 --- [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [FlowJob: [name=deciderJob]] launched with the following parameters: [{}]
2024-12-16T23:36:56.636+09:00  INFO 80542 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [startStep]
2024-12-16T23:36:56.643+09:00  INFO 80542 --- [           main] c.j.s.jojoldu.DeciderJobConfiguration    : >>>>> startStep was executed!
2024-12-16T23:36:56.647+09:00  INFO 80542 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [startStep] executed in 11ms
2024-12-16T23:36:56.655+09:00  INFO 80542 --- [           main] c.j.s.jojoldu.DeciderJobConfiguration    : random number : 5
2024-12-16T23:36:56.664+09:00  INFO 80542 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [oddStep]
2024-12-16T23:36:56.670+09:00  INFO 80542 --- [           main] c.j.s.jojoldu.DeciderJobConfiguration    : >>>>> 홀수입니다.
2024-12-16T23:36:56.672+09:00  INFO 80542 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [oddStep] executed in 7ms
2024-12-16T23:36:56.682+09:00  INFO 80542 --- [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [FlowJob: [name=deciderJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 60ms
```

* `start()` : Job Flow의 첫 번째 step을 시작한다.
* `next()` : startStep 이후에 decider을 실행한다.
* `from()` : from은 이벤트 리스너 역할을 수행한다. decider의 상태값을 보고 일치하는 상태라면 `to()`에 포함된 step을 호출한다.

### ✅ Spring Batch Scope & Job Parameter

* Spring Batch의 경우 외부 혹은 내부에서 파라미터를 받아 여러 Batch 컴포넌트에서 사용할 수 있게 지원하고 있다.
* 이 파라미터를 JOB_PARAMETER라고 한다.
* JOB_PARAMETER를 사용하기 위해선 항상 Spring Batch 전용 Scope를 선언해야만 한다.
* 크게 `@StepScope`와 `@JobScope`가 있다. 사용법은 SpEL로 선언해서 사용하면 된다.

```java
@Value("#{jobParameters[파라미터명]}")
```

* `@JobScope` 어노테이션은 Step 선언문에서 사용 가능하고, `@StepScope` 어노테이션은 Tasklet, ItemReader, ItemWriter, ItemProcessor에서 사용할 수 있다.
* 현재 Job Parameter 타입으로 사용할 수 있는 것은 Double, Long, Date, String이 있다.
* LocalDate와 LocalDateTime의 경우 String으로 타입 변환을 하여 사용해야만 한다.

### ✅ @JobScope & @StepScope

* Spring Batch 컴포넌트(Tasklet, ItemReader, ItemWriter, ItemProcessor) 등에 `@StepScope`를 사용하게 되면 Spring Batch가 Spring 컨테이너를 통해 지정된 Step을 통해 지정된 **Step의 실행 시점**에 해당 컴포넌트를 Spring Bean으로 생성한다.
* `@JobScope`는 **Job 실행 시점**에 빈이 생성된다.
* 이 어노테이션을 사용함으로써 얻을 수 있는 장점은 크게 2가지가 있다.
  * Job Parameter의 Late Binding이 가능하다.
    * Application이 실행되는 시점이 아니더라도 Controller나 Service와 같은 비즈니스 로직 처리 단계에서 Job Parameter를 할당시킬 수 있다.
  * 동일한 컴포넌트를 병렬 혹은 동시에 사용할 때 유용하다.
    * 각각의 step에서 별도의 tasklet을 생성하고 관리하기 때문에 서로의 상태를 침범할 일이 없다.

### ✅ @JobScope & @StepScope 주의사항

```java
@Scope(value = "step", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StepScope {

}
```
* `@StepScope` 내부를 들여다 보면 위와 같다.
* `@Bean` 어노테이션과 `@StepScope` 어노테이션을 같이 사용하게 되면 `@Scope (value = "step", proxyMode = TARGET_CLASS)`로 명시하는 것이 좋다.
* 관련 레퍼런스 : [Spring Batch에서 @StepScope 어노테이션 사용시 주의사항](https://jojoldu.tistory.com/132)

### ✅ Chunk

* Spring Batch에서의 Chunk란, 데이터 덩어리로 작업을 할 때 각 커밋 사이에 처리되는 row 수를 얘기한다.
* 즉, Chunk 지향 처리란, 한 번에 하나씩 데이터를 읽어 Chunk라는 덩어리로 만든 뒤, Chunk 단위로 트랜잭션을 다루는 것을 말한다.
* Chunk 단위로 트랜잭션을 수행하기 때문에 실패할 경우엔 해당 Chunk만 롤백이 되고, 이전에 커밋된 트랜잭션 범위까지는 반영이 된다.
* 절차
  * Reader에서 데이터를 하나 읽어온다.
  * 읽어온 데이터를 Processor에서 가공한다.
  * 가공된 데이터들을 별도의 공간에 모은 뒤, Chunk 단위만큼 쌓이게 되면 Writer에 전달하고 Writer는 일괄 저장한다.
> **Reader와 Processor에서는 1건씩, Writer에선 Chunk 단위**

<img src="/jojoldu/chunk.png">

> chunkProcessor.provide(contribution) : Reader에서 Chunk 사이즈만큼 데이터를 가져온다.

<img src="/jojoldu/simplechunkprovider.png">

* `chunkProcessor.provide(contribution)`를 보면 위와 같다.
* `read()` 메서드를 통해 데이터를 읽되 item이 null이 될 떄까지 읽는다.

> chunkProcessor.process(contriubution, inputs) : Reader로 받은 데이터를 가공하고 저장

<img src="/jojoldu/simplechunkprocessor.png">

* `transform()`을 통해 가공된 대량의 데이터는 `write()`을 통해 일괄 저장된다.
* `write()`는 저장이 될 수도 있고 외부 API로 전송할 수도 있다.