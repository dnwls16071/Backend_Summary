package intermediate1.inner;

public class Outer {

	private int outInstanceValue = 3;

	public void process(int paramVar) {

		int localVar = 1;

		class Local {
			int value = 0;

			public void printData() {
				System.out.println("outInstanceValue = " + Outer.this.outInstanceValue);
                System.out.println("paramVar = " + paramVar);
                System.out.println("localVar = " + localVar);
                System.out.println("value = " + value);
			}
		}

		Local local = new Local();
		local.printData();
	}
}
