package advanced1.ch4;

import static advanced1.util.Logger.log;

public class ThreadStateMain {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new MyRunnable(), "myThread");
		log("thread.state1 = " + thread.getState());
		log("thread.start");
		thread.start();
		Thread.sleep(1000);
		log("thread.state3 = " + thread.getState());
		log("sleep end");
		Thread.sleep(4000);
		log("thread.state5 = " + thread.getState());
	}

	static class MyRunnable implements Runnable {

		@Override
		public void run() {
			try {
				log("start");
				log("thread.state2 = " + Thread.currentThread().getState());
				log("thread.sleep");
				Thread.sleep(3000);
				log("sleep end");
				log("thread.state4 = " + Thread.currentThread().getState());
				log("end");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
