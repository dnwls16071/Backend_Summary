package intermediate1.inner;

public class NestedOuter {

	private static int outClassValue = 1;
	private int outInstanceValue = 2;

	static class Nested {

		private int nestedInnerInstanceValue = 3;

		public void print() {
			System.out.println("nestedInnerInstanceValue = " + nestedInnerInstanceValue);	// 자신의 멤버 변수에 접근
			// System.out.println("outInstanceValue = " + outInstanceValue);				// 바깥 클래스의 멤버 변수에 접근 불가능
			System.out.println("outClassValue = " + outClassValue);							// 바깥 클래스의 클래스 멤버에 접근
		}
	}
}
