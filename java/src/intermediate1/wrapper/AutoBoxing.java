package intermediate1.wrapper;

public class AutoBoxing {

	public static void main(String[] args) {

		int value = 7;
		Integer i1 = Integer.valueOf(value);	// 박싱(boxing)

		int i2 = i1.intValue(); // 언박싱(unboxing)

		System.out.println(i1);
		System.out.println(i2);
	}
}
