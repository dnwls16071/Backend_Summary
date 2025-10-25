package advanced1.thread;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountImplV3 implements BankAccount {

	private int balance;	// 잔고(자원)

	public BankAccountImplV3(int balance) {
		this.balance = balance;
	}

	@Override
	public boolean withdraw(int amount) {
		log("거래 시작: " + getClass().getSimpleName());

		// synchronized 코드 블럭 단위로..
		synchronized (this) {
			log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
			if (balance < amount) {

				log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
				return false;
			}

			log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
			sleep(1000);
			balance = balance - amount;
			log("[출금 완료] 출금액: " + amount + ", 변경 잔액: " + balance);
		}

		log("거래 종료");
		return true;
	}

	@Override
	public synchronized int getBalance() {
		return balance;
	}
}
