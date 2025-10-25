package advanced1.ch5;

import static advanced1.util.Logger.log;
import static advanced1.util.ThreadUtils.sleep;

public class ThreadStopMain {

	public static void main(String[] args) {
		MyTask myTask = new MyTask();
		Thread thread = new Thread(myTask, "task");
		thread.start();

		sleep(4000);
		thread.interrupt();
		log("인터럽트 상태 1 = " + thread.isInterrupted());
	}

	static class MyTask implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					log("작업 중");
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				log("인터럽트 상태 2 = " + Thread.currentThread().isInterrupted());
				log("인터럽트 메시지 = " + e.getMessage());
				log("상태 = " + Thread.currentThread().getState());
				log("자원 정리");
				log("작업 종료");
			}
		}
	}
}
