package advanced1.ch2;

import static advanced1.util.Logger.log;

public class InnerRunnableMainV2 {
	public static void main(String[] args) {

		log("main() start");

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();

		log("main() end");
	}

}
