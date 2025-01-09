package advanced1.ch7;

public class SyncTest1BadMain {

	public static void main(String[] args) throws InterruptedException {

		Counter counter = new Counter();

		Runnable task = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					counter.increment();
				}
			}
		};

		Thread thread1 = new Thread(task);
		Thread thread2 = new Thread(task);

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		System.out.println("결과 조회: " + counter.getcount());
	}

	static class Counter {

		private int count = 0;	// 공유 자원

		public synchronized void increment() {
				count += 1;
		}

		public synchronized int getcount() {
			return count;
		}
	}
}
