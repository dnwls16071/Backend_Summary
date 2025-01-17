package advanced1.ch6;

import static advanced1.util.Logger.log;
import static util.ThreadUtils.sleep;

public class VolatileFlagMain {
	public static void main(String[] args) {

		MyTask myTask = new MyTask();
		Thread thread = new Thread(myTask, "task");
		thread.start();
		sleep(1000);

		myTask.flag = false;
		log("flag = " + myTask.flag + ", count = " + myTask.count + " in main()");
	}

	static class MyTask implements Runnable {

		volatile boolean flag = true;
		volatile long count;

		@Override
		public void run() {
			while (flag) {
				count++;
				if (count % 100_000_000 == 0) {
					log("flag = " + flag + ", count = " + count + ", in while()");
				}
			}
			log("flag = " + flag + ", count = " + count + " 종료");
		}
	}
}
