package advanced1.ch2;

import static advanced1.util.Logger.log;

public class InnerRunnableMainV1 {
	public static void main(String[] args) {

		log("main() start");

		InnerRunnable innerRunnable = new InnerRunnable();
		Thread thread = new Thread(innerRunnable);
		thread.start();

		log("main() end");
	}

	static class InnerRunnable implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}
}
