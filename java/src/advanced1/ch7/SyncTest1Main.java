package advanced1.ch7;

import static util.MyLogger.log;

public class SyncTest1Main {

	public static void main(String[] args) {

		MyCounter myCounter = new MyCounter();

		Runnable task = new Runnable() {
			@Override
			public void run() {
				myCounter.count();
			}
		};

		Thread thread1 = new Thread(task, "thread1");
		Thread thread2 = new Thread(task, "thread2");

		thread1.start();
		thread2.start();
	}

	static class MyCounter {

		public void count() {
			int localValue = 0;
			for (int i = 0; i < 1000; i++) {
				localValue++;
			}
			log("결과: " + localValue);
		}
	}
}
