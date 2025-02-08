package com.jwj.spring_principle2.templateMethod;

public class TemplateMethodMain {

	public static void main(String[] args) {

		AbstractTemplate template1 = new SubClassLogic1();
		template1.call();
		AbstractTemplate template2 = new SubClassLogic2();
		template2.call();
	}
}
