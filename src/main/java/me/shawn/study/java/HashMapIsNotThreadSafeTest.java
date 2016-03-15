package me.shawn.study.java;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * hashMap 이 왜 쓰레드 안전하지 않은지 확인해보는 코드.
 *
 * AlwaysSameHashcodeObject 의 hashCode 를 일부러 항상 동일하게 맞췄을 경우(hashCode 오동작 유도)
 *
 * (1) hashMap 은 여러 쓰레드에서 동일 key 로 천번 넣으면 1개만 들어가는게 정상 동작인데, size 가 3, 4개로 출력되는 경우가 발견된다.
 *
 * (2) concurrentHashMap 은 여러 쓰레드에서 동일 key 로 천번 넣어도 항상 size 1개 로 정상 동작한다.
 *
 * hashMap 은 put 내부에서 단일 연산이 이뤄지지 않아서 아이템 추가 및 size 증가의 경우 race condition 이 발생하여 size 가 오동작 한다.
 *
 * concurrentHashMap 은 thread safe 하므로 put 내부에서 아이템 추가 및 size 증가가 단일 연산으로 처리된다.
 *
 * AlwaysSameHashcodeObject 가 아닌 String 으로 해봐도 동일한 결과가 발생함. put 을 여러번 수행할 때 size 가 오동작.
 *
 * @author shawn.ctl
 */
public class HashMapIsNotThreadSafeTest {

	public static void main(String[] args) throws InterruptedException {
		final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		final ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<String, Integer>();

		System.out.println("============ HashMap ==========");
		test(hashMap);

		System.out.println("============ ConcurrentHashMap ==========");
		test(concurrentHashMap);

	}

	private static void test(final Map<String, Integer> hashMap) throws InterruptedException {
		final String id = "shawn";

		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			final int finalI = i;
			service.execute(new Runnable() {
				public void run() {
					hashMap.put(id, finalI);
				}
			});
		}

		TimeUnit.SECONDS.sleep(1);

		service.shutdown();

		System.out.println("expected map size is '1', test result is '" + hashMap.size() + "'");
		for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
			System.out.println("entry : " + entry);
		}

	}
}

class AlwaysSameHashcodeObject {
	private String name = "name";

	public AlwaysSameHashcodeObject(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return 1; // TODO 항상 동일한 해시코드가 나오는 객체.
	}

	@Override
	public boolean equals(Object o) {
		AlwaysSameHashcodeObject to = (AlwaysSameHashcodeObject) o;
		return this.name.equals(to.getName());
	}
}
