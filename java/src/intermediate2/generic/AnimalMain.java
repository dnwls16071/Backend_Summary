package intermediate2.generic;

public class AnimalMain {
	public static void main(String[] args) {

		Animal cat = new Cat("cat", 10);
		Animal dog = new Dog("dog", 20);

		cat.sound();
		dog.sound();

		System.out.println(cat);
		System.out.println(dog);

		Box<Animal> box = new Box<>();
		box.setValue(cat);
		Animal animal = box.get();
		System.out.println("animal = " + animal);
	}
}
