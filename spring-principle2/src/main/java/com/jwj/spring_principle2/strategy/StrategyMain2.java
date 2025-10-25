package com.jwj.spring_principle2.strategy;

public class StrategyMain2 {

	public static void main(String[] args) {

		ContextV2 context = new ContextV2();
		context.execute(new StrategyLogic1());
		context.execute(new StrategyLogic2());
	}
}
