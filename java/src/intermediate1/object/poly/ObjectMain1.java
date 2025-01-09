package intermediate1.object.poly;

public class ObjectMain1 {

	public static void main(String[] args) {

		Car car = new Car();
		Dog dog = new Dog();

		action(car);
		action(dog);
	}

	private static void action(Object object) {
		if (object instanceof Car car) {
			car.sound();
		} else if (object instanceof Dog dog) {
			dog.sound();
		}
	}
}
