package intermediate2.hashset;

public class HashCodeMain {
	public static void main(String[] args) {

		Member member1 = new Member("id1");
		Member member2 = new Member("id1");

		System.out.println(member1.hashCode());
		System.out.println(member2.hashCode());

		System.out.println(member1.equals(member2));
	}
}
