package advanced1.ch4;

import advanced1.ch2.HelloRunnable;

import static advanced1.util.Logger.log;

public class ThreadInfoMain {

	public static void main(String[] args) {

		Thread mainThread = Thread.currentThread();
		log("thread = " + mainThread);
		log("thread.threadId = " + mainThread.getId());
		log("thread.getName = " + mainThread.getName());
		log("thread.getPriority = " + mainThread.getPriority());
		log("thread.getThreadGroup = " + mainThread.getThreadGroup());
		log("thread.getState = " + mainThread.getState());

		Thread thread = new Thread(new HelloRunnable(), "thread");
		log("thread = " + thread);
		log("thread.threadId = " + thread.getId());
		log("thread.getName = " + thread.getName());
		log("thread.getPriority = " + thread.getPriority());
		log("thread.getThreadGroup = " + thread.getThreadGroup());
		log("thread.getState = " + thread.getState());
	}
}
