package intermediate2.comp;

import java.util.Arrays;

public class UserMain {
	public static void main(String[] args) {

		UserV1 a = new UserV1("a", 10);
		UserV1 b = new UserV1("b", 20);
		UserV1 c = new UserV1("c", 30);

		UserV1[] list = {c, b, a};
		System.out.println(Arrays.toString(list));

		Arrays.sort(list);
		System.out.println(Arrays.toString(list));

		UserV2 a1 = new UserV2("a", 10);
		UserV2 b1 = new UserV2("b", 20);
		UserV2 c1 = new UserV2("c", 30);

		UserV2[] list1 = {c1, b1, a1};
		System.out.println(Arrays.toString(list1));

		Arrays.sort(list1, new IdComparator());	// 별도로 구현해서 전달
		System.out.println(Arrays.toString(list1));
	}
}
