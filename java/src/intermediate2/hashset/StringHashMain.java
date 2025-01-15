package intermediate2.hashset;

public class StringHashMain {

	private static final int CAPACITY = 10;

	public static void main(String[] args) {

		//		hashCode("A");
		//		hashCode("B");
		//		hashCode("AB");

		System.out.println(hashIndex(hashCode("A")));
		System.out.println(hashIndex(hashCode("B")));
		System.out.println(hashIndex(hashCode("AB")));
	}

	static int hashCode(String str) {
		char[] charArray = str.toCharArray();
		int sum = 0;
		for (char c : charArray) {
			sum += c;
		}
		return sum;
	}

	static int hashIndex(int value) {
		return value % CAPACITY;
	}
}
