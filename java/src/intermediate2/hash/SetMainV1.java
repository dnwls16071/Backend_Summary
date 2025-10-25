package intermediate2.hash;

public class SetMainV1 {
	public static void main(String[] args) {

		CustomSet customSet = new CustomSet();
		customSet.add(1);
		customSet.add(2);
		customSet.add(3);
		customSet.add(4);
		System.out.println(customSet);

		boolean contains1 = customSet.contains(2);
		System.out.println(contains1);

		boolean contains2 = customSet.contains(5);
		System.out.println(contains2);
	}
}
