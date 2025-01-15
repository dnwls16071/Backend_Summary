package intermediate2.generic;

public class GenericMethod {

	public static <T> T GenericMethod1(T obj) {
		System.out.println("obj = " + obj);
		return obj;
	}

	public static <T extends Animal> T GenericMethod2(T obj) {
		obj.sound();
		return obj;
	}

	public static void main(String[] args) {
		GenericMethod1(new Object());
		GenericMethod2(new Cat("cat", 30));
	}
}
