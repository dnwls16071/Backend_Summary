package intermediate1.immutable;

public class RefMain2 {

	public static void main(String[] args) {

		ImmutableAddress address1 = new ImmutableAddress("Seoul");
		ImmutableAddress address2 = address1;

		address2 = new ImmutableAddress("Daegu");

		System.out.println(address1);
		System.out.println(address2);
	}
}
