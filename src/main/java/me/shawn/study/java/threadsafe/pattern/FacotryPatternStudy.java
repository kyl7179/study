package me.shawn.study.java.threadsafe.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn.ctl
 */
public class FacotryPatternStudy {
	public static void main(String[] args) throws InterruptedException {
		exampleCommonCreation();
		exampleFacotoryCreation();
	}

	private static void exampleFacotoryCreation() {
		System.out.println("======= exampleFacotoryCreation");

		Obj obj = ObjFactory.create();
		obj.printList();

		// NOTICE: 객체 생성시 사용된 list 에 다시 접근할 수 없다. (safe)
	}

	private static void exampleCommonCreation() throws InterruptedException {
		System.out.println("======= exampleCommonCreation");

		final List<String> dangerousList = new ArrayList<String>();
		final Obj obj = new Obj(dangerousList);
		obj.printList();

		Thread anotherThread = new Thread(new Runnable() {
			public void run() {
				dangerousList.add("added by another thread");
			}
		});

		anotherThread.start();
		anotherThread.join();

		obj.printList();
		System.out.println("NOTICE: 다른 쓰레드에서 list 에 add 했다. (Not Thread Safe)");
	}
}
