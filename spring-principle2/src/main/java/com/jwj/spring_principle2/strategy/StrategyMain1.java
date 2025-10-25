package com.jwj.spring_principle2.strategy;

public class StrategyMain1 {

	public static void main(String[] args) {

		Strategy strategy1 = new StrategyLogic1();
		ContextV1 contextV1 = new ContextV1(strategy1);
		contextV1.execute();
	}
}
