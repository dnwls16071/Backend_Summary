package intermediate1.object.equal;

public class EqualsMain {

	public static void main(String[] args) {

		User user1 = new User("user", 10);
		User user2 = new User("user", 10);

		System.out.println(user1.equals(user2));
		System.out.println(user2.equals(user1));
	}
}
