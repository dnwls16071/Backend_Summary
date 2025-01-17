package advanced1.ch2;

public class DaemonThreadMain {

	public static void main(String[] args) {

		System.out.println(Thread.currentThread().getName());
		DaemonThread daemonThread = new DaemonThread();
		daemonThread.setDaemon(true);	// 데몬 쓰레드로 설정
		daemonThread.start();
		System.out.println(Thread.currentThread().getName());
	}

	static class DaemonThread extends Thread {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(10000);	// 10초를 기다린다.
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName());
		}
	}
}
