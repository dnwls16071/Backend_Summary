package intermediate2.arraylist;

public class ArrayMainV4 {

	public static void main(String[] args) {

		CustomArrayListV3 customArrayList = new CustomArrayListV3(3);

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
		System.out.println(customArrayList);

		customArrayList.add(4, "d");
		customArrayList.add(5, "e");
		System.out.println(customArrayList);

		customArrayList.remove(0);
		System.out.println(customArrayList);

		customArrayList.remove(5);
		System.out.println(customArrayList);
	}
}
