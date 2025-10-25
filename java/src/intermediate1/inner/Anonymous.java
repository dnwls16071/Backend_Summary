package intermediate1.inner;

public class Anonymous {

	private static int outerClassValue = 1;
	private int outerInstanceValue = 2;

	public void process(int paramVar) {

		Printer printer = new Printer() {

			int value = 0;

			@Override
			public void print() {
				System.out.println(value);
				System.out.println(paramVar);
				System.out.println(outerInstanceValue);
				System.out.println(outerClassValue);
			}
		};

		printer.print();
	}
}
