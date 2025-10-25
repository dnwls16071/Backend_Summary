package intermediate1.clazz;

public class ClassCreateMain {
	public static void main(String[] args) throws Exception {

		Class<Hello> helloClass = Hello.class;
		// Class<?> helloClass1 = Class.forName("java.clazz.Hello");

		// 생성자를 선택하고 선택된 생성자를 기반으로 인스턴스를 생성한다.
		Hello hello = helloClass.getDeclaredConstructor().newInstance();
		String result = hello.hello();
		System.out.println("result = " + result);
	}
}
