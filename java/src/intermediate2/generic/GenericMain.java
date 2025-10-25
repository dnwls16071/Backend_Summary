package intermediate2.generic;

public class GenericMain {
	public static void main(String[] args) {

		Generic<Integer> integerGeneric = new Generic<>(3);
		Generic<String> stringGeneric = new Generic<>("string");
		Generic<Double> doubleGeneric = new Generic<>(3.14);

		System.out.println("integerGeneric = " + integerGeneric);
		System.out.println("stringGeneric = " + stringGeneric);
		System.out.println("doubleGeneric = " + doubleGeneric);
	}
}
