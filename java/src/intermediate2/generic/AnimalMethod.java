package intermediate2.generic;

public class AnimalMethod {

	public static <T> void printGeneric(Box<T> box) {
		System.out.println(box.get());
	}

	public static void printWildCardV1(Box<?> box) {
		System.out.println(box.get());
	}

	public static void printWildCardV2(Box<? extends Animal> box) {
		Animal animal = box.get();
		System.out.println(animal.getName());
		System.out.println(animal.getSize());
	}
}
