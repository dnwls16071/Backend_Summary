package advanced1.ch2;

public class CustomThread extends Thread{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
}
