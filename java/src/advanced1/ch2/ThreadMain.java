package advanced1.ch2;

public class ThreadMain {
	public static void main(String[] args) {
		CustomThread customThread = new CustomThread();
		System.out.println(Thread.currentThread().getName());
		customThread.start();
		System.out.println(Thread.currentThread().getName());
	}
}
