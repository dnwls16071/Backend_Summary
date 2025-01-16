package intermediate2.comp;

public class UserV2 {

	private String id;
	private int age;

	public UserV2(String id, int age) {
		this.id = id;
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", age=" + age +
				'}';
	}

	public String getId() {
		return id;
	}

	public int getAge() {
		return age;
	}

}
