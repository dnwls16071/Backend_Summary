package intermediate1.string;

public class StringBuilderMain1 {

	public static void main(String[] args) {

		StringBuilder sb = new StringBuilder();
		sb.append("A");
		sb.append("B");
		sb.append("C");
		sb.append("D");
		sb.append("E");

		// StringBuilder(가변) -> String(불변)
		System.out.println(sb.toString());
	}
}
