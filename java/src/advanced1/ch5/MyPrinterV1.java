package advanced1.ch5;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static advanced1.util.Logger.log;

public class MyPrinterV1 {

	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread thread = new Thread(printer, "printer");
		thread.start();

		Scanner scanner = new Scanner(System.in);

		while (true) {
			String input = scanner.nextLine();
		    if ("exit".equalsIgnoreCase(input)) {
                thread.interrupt();	// 인터럽트를 건다.
                break;
            }
			printer.addJob(input);
		}
	}

	static class Printer implements Runnable {

		Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

		@Override
		public void run() {
			while (true) {

				try {
					String job = jobQueue.poll();
					log("출력 시작 = " + job + ", 대기 문서 = " + jobQueue);
					Thread.sleep(3000);
					log("출력 완료");
				} catch (InterruptedException e) { // 인터럽트 해결
					log("인터럽트");
					break;
				}

				log("프린터 종료");
			}
		}

		public void addJob(String input) {
			jobQueue.offer(input);
		}
	}
}
