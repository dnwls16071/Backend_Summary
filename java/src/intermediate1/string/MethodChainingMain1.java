package intermediate1.string;

public class MethodChainingMain1 {

	public static void main(String[] args) {

		StringBuilder result = new StringBuilder("A").append("B").append("C").append("D").append("E").append("F");
		System.out.println(result);
	}
}