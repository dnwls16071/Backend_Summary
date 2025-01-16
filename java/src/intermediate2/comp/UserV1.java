package intermediate2.comp;

public class UserV1 implements Comparable<UserV1> {

	private String id;
	private int age;

	public UserV1(String id, int age) {
		this.id = id;
		this.age = age;
	}

	@Override
	public int compareTo(UserV1 o) {
		return this.age < o.age ? -1 : (this.age == o.age) ? 0 : 1;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", age=" + age +
				'}';
	}
}
