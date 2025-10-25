package intermediate1.object.poly;

public class ObjectMain2 {

	public static void main(String[] args) {

		Car car = new Car();
		Dog dog = new Dog();

		Object[] objects = {car, dog};

		action(objects[0]);
		action(objects[1]);
	}

	private static void action(Object object) {
		if (object instanceof Car car) {
			car.sound();
		} else if (object instanceof Dog dog) {
			dog.sound();
		}
	}
}
