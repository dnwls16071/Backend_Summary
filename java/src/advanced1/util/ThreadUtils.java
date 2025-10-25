package advanced1.util;

import static advanced1.util.Logger.log;

public abstract class ThreadUtils {

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log("인터셉트 발생 = " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
