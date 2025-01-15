package intermediate2.arraylist;

import java.util.Arrays;

public class ArrayMainV1 {

	public static void main(String[] args) {

		int[] arr = new int[5];
		arr[0] = 1;
		arr[1] = 2;
		arr[2] = 3;
		arr[3] = 4;
		arr[4] = 5;
		System.out.println(Arrays.toString(arr));

		arr[2] = 10;
		System.out.println(Arrays.toString(arr));

		int target = 10;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == target) {
				System.out.println((i + 1) + "번째에 존재한다.");
				break;
			}
		}
	}
}
