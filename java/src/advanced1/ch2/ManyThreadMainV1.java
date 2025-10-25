package advanced1.ch2;

import static advanced1.util.Logger.log;

public class ManyThreadMainV1 {
	public static void main(String[] args) {

		log("main() start");

		HelloRunnable helloRunnable = new HelloRunnable();
		Thread thread1 = new Thread(helloRunnable);
		Thread thread2 = new Thread(helloRunnable);
		Thread thread3 = new Thread(helloRunnable);

		thread1.start();
		thread2.start();
		thread3.start();

		log("main() end");
	}
}
