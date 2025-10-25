package advanced1.thread;

import static util.MyLogger.log;

public class BankAccountMain {

	public static void main(String[] args) throws InterruptedException {

		//BankAccount bankAccount = new BankAccountImplV1(1000);
		//BankAccount bankAccount = new BankAccountImplV2(1000);
		BankAccount bankAccount = new BankAccountImplV3(1000);

		Thread thread1 = new Thread(new WithdrawTask(bankAccount, 800), "t1");
		Thread thread2 = new Thread(new WithdrawTask(bankAccount, 800), "t2");

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		log("최종 잔액: " + bankAccount.getBalance());
	}
}
