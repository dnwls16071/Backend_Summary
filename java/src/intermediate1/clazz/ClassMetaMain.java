package intermediate1.clazz;

import java.lang.reflect.Method;

public class ClassMetaMain {
	public static void main(String[] args) throws ClassNotFoundException {

		Class<String> clazz = String.class;								// 클래스 조회
		Class<? extends String> clazz1 = new String().getClass();       // 인스턴스 조회
		Class<?> clazz2 = Class.forName("java.lang.String");  // 문자열 조회

		// 모든 메서드 출력
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println("method = " + method);
		}

		String superClass = clazz.getSuperclass().getName();
		System.out.println("상위 클래스 정보 : " + superClass);

		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> anInterface : interfaces) {
			System.out.println("인터페이스 정보 : " + anInterface);
		}
	}
}
