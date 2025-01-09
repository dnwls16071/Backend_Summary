package intermediate1.object.toString;

public class Car {

	private String carName;

	public Car(String carName) {
		this.carName = carName;
	}

	// toString() 메서드 재정의
	@Override
	public String toString() {
		return "Car{" +
				"carName='" + carName + '\'' +
				'}';
	}
}
