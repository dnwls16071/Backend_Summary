package advanced1.thread;

public class WithdrawTask implements Runnable {

	private final BankAccount bankAccount;
	private final int amount;

	public WithdrawTask(BankAccount bankAccount, int amount) {
		this.bankAccount = bankAccount;
		this.amount = amount;
	}

	@Override
	public void run() {
		bankAccount.withdraw(amount);
	}
}
