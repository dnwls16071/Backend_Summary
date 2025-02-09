package com.jwj.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6 {

	@Around("com.jwj.aop.order.Pointcuts.orderAndService()")
	public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable
	{
		try {
			//@Before : 조인 포인트 실행 이전
			log.info("[around][트랜잭션 시작] {}", joinPoint.getSignature());
			Object result = joinPoint.proceed();
			//@AfterReturning : 조인 포인트 정상 완료된 후 실행
			log.info("[around][트랜잭션 커밋] {}", joinPoint.getSignature());
			return result;
		} catch (Exception e) {
			//@AfterThrowing : 메서드가 예외를 던진 경우 실행
			log.info("[around][트랜잭션 롤백] {}", joinPoint.getSignature());
			throw e;
		} finally {
			//@After : 정상 또는 예외와 관계없이 실행
			log.info("[around][리소스 릴리즈] {}", joinPoint.getSignature());
		}
	}

	@Before("com.jwj.aop.order.Pointcuts.orderAndService()")
	public void doBefore(JoinPoint joinPoint) {
		log.info("[before] {}", joinPoint.getSignature());
	}

	@AfterReturning(value = "com.jwj.aop.order.Pointcuts.orderAndService()", returning = "result")
	public void doReturn(JoinPoint joinPoint, Object result) {
		log.info("[return] {} return={}", joinPoint.getSignature(), result);
	}

	@AfterThrowing(value = "com.jwj.aop.order.Pointcuts.orderAndService()", throwing = "ex")
	public void doThrowing(JoinPoint joinPoint, Exception ex) {
		log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
	}

	@After("com.jwj.aop.order.Pointcuts.orderAndService()")
	public void doAfter(JoinPoint joinPoint) {
		log.info("[after] {}", joinPoint.getSignature());
	}
}
