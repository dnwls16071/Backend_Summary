package intermediate1.object.toString;

public class ToStringMain1 {

	public static void main(String[] args) {

		Object o = new Object();
		String string = o.toString();

		System.out.println(string);				// 결과는 똑같다.
		System.out.println(string.toString());	// 결과는 똑같다.
	}
}
