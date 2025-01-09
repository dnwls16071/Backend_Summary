package intermediate1.immutable;

public class ImmutableMain {

	public static void main(String[] args) {

		ImmutableObj obj = new ImmutableObj(1);
		ImmutableObj result = obj.add(4);

		System.out.println(obj.getValue());
		System.out.println(result.getValue());

	}
}
