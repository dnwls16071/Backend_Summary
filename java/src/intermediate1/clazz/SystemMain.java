package intermediate1.clazz;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class SystemMain {
	public static void main(String[] args) {

		// 현재 시간(밀리초)
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println("currentTimeMillis = " + currentTimeMillis);

		// 현재 시간(나노초)
		long currentTimeNano = System.nanoTime();
		System.out.println("currentTimeNano = " + currentTimeNano);

		// 환경 변수
		Map<String, String> getenv = System.getenv();
		for (String env : getenv.keySet()) {
			System.out.println("env = " + env);
		}

		// 시스템 속성
		Properties properties = System.getProperties();
		System.out.println("properties = " + properties);

		// 배열 고속 복사
		char[] originalArray = {'h', 'e', 'l', 'l', 'o'};
		char[] copiedArray = new char[originalArray.length];
		System.arraycopy(originalArray, 0, copiedArray, 0, originalArray.length);

		System.out.println("copiedArray: " + Arrays.toString(copiedArray));
	}
}
