package advanced1.ch4;

import static advanced1.util.Logger.log;
import static advanced1.util.ThreadUtils.sleep;

public class ThreadSumMain {

	public static void main(String[] args) throws InterruptedException {
		HelloThread task1 = new HelloThread(1, 50);
		HelloThread task2 = new HelloThread(51, 100);

		Thread thread1 = new Thread(task1, "thread1");
		Thread thread2 = new Thread(task2, "thread2");

		thread1.start();
		thread2.start();

		//thread1.join();
		//thread2.join();

		thread1.join(10000);
		thread2.join(10000);

		log("task1.result = " + task1.result);
		log("task2.result = " + task2.result);

		int sum = task1.result + task2.result;
		log("sum = " + sum);
	}

	static class HelloThread implements Runnable {

		int start;
		int end;
		int result = 0;

		public HelloThread(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			sleep(2000);
			int sum = 0;
			for (int i = start; i <= end; i++) {
				sum += i;
			}
			result = sum;
		}
	}
}
