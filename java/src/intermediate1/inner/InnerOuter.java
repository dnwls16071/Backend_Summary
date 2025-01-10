package intermediate1.inner;

public class InnerOuter {

	private static int outClassValue = 1;
	private int outInstanceValue = 2;

	class Inner {
		private int innerInstanceValue = 3;

		public void print() {
			System.out.println("innerInstanceValue = " + innerInstanceValue);	// 자신의 멤버 변수에 접근 가능
            System.out.println("outInstanceValue = " + outInstanceValue);		// 바깥 클래스 멤버 변수에 접근 가능
            System.out.println("outClassValue = " + outClassValue);				// 바깥 클래스의 클래스 멤버에 접근 가능
		}
	}
}
