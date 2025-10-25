package intermediate1.immutable;

public class RefMain1 {

	public static void main(String[] args) {

		Address address1 = new Address("Seoul");	// 참조형 변수
		Address address2 = address1;

		address2.setValue("Daegu");

		System.out.println(address1);	// 얘도 Daegu
		System.out.println(address2);	// 얘도 Daegu
	}
}
