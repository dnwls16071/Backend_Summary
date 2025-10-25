package intermediate2.generic;

public class Hospital<T extends Animal> {

	private T animal;

	public T getAnimal() {
		return animal;
	}

	public void setAnimal(T animal) {
		this.animal = animal;
	}

	public void checkUp() {
		System.out.println(animal.getName());
		System.out.println(animal.getSize());
		animal.sound();
	}

	public T bigger(T target) {
		return animal.getSize() > target.getSize() ? animal : target;
	}

	@Override
	public String toString() {
		return "Hospital{" +
				"animal=" + animal +
				'}';
	}
}
