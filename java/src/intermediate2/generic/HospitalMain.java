package intermediate2.generic;

public class HospitalMain {
	public static void main(String[] args) {

		Animal dog = new Dog("멍멍쓰", 10);
		Animal cat  = new Cat("야옹쓰", 20);

		Hospital<Animal> animalHospital = new Hospital<>();

		animalHospital.setAnimal(dog);
		System.out.println(animalHospital.getAnimal());

		animalHospital.setAnimal(cat);
		System.out.println(animalHospital.getAnimal());
	}
}
