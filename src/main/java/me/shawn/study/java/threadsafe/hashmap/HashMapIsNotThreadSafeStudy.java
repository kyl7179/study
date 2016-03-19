package me.shawn.study.java.threadsafe.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * hashMap 이 왜 쓰레드 안전하지 않은지 확인해보는 코드.
 *
 * (1) hashMap 은 여러 쓰레드에서 동일 key 로 천번 넣어도 1개만 들어가는게 기대되는 동작인데, size 가 1이 아닌 경우가 발견된다.
 * 
 * (2) concurrentHashMap 은 여러 쓰레드에서 동일 key 로 천번 넣어도 항상 size 1개 로 정상 동작한다.
 * 
 * hashMap 은 put 내부에서 단일 연산이 이뤄지지 않아서 아이템 추가 및 size 증가의 경우 race condition 이 발생하여 size 가 오동작 한다.
 * 
 * concurrentHashMap 은 thread safe 하므로 put 내부에서 아이템 추가 및 size 증가가 단일 연산으로 처리된다.
 *
 * @author shawn.ctl
 */
public class HashMapIsNotThreadSafeStudy {
	public static void main(String[] args) throws InterruptedException {
		final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		final ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<String, Integer>();

		System.out.println("각 맵에 1000개 추가(여러쓰레드로 동시에)");
		System.out.println("============ HashMap ==========");
		test(hashMap);

		System.out.println("============ ConcurrentHashMap ==========");
		test(concurrentHashMap);
	}

	private static void test(final Map<String, Integer> map) throws InterruptedException {
		final String id = "shawn";

		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			final int finalI = i;
			service.execute(new Runnable() {
				public void run() {
					map.put(id, finalI);
				}
			});
		}

		TimeUnit.SECONDS.sleep(1);

		service.shutdown();

		System.out.println("expected map size is '1', test result is '" + map.size() + "'");
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println("entry : " + entry);
		}
	}
}
