package intermediate2.arraylist;

public class ArrayMainV2 {

	public static void main(String[] args) {

		CustomArrayListV1 customArrayList = new CustomArrayListV1(3);

		customArrayList.add("a");
		customArrayList.add("b");
		customArrayList.add("c");
		System.out.println(customArrayList);

		Object o = customArrayList.get(2);
		System.out.println(o);

		int size = customArrayList.size();
		System.out.println(size);

		int index = customArrayList.indexOf("a");
		System.out.println(index);

		customArrayList.add("f");
		System.out.println(customArrayList); // Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3

	}
}
