package com.jwj.springPrinciple.singleton;

public class StatefulService {

	public int order(String name, int price) {
        System.out.println("상태가 있는(stateful) 서비스: " + name + " - " + price);
		return price;
	}
}
