package advanced1.ch2;

public class HelloRunnableMain {
	public static void main(String[] args) {

		System.out.println(Thread.currentThread().getName());
		Thread thread = new Thread(new HelloRunnable());
		thread.start();
		System.out.println(Thread.currentThread().getName());
	}
}
