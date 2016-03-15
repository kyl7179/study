package me.shawn.study.java;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author shawn.ctl
 */
public class HashMapIsNotThreadSafeSecondTest {
	public static void main(String[] args) throws InterruptedException {
		final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		final ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<String, Integer>();

		System.out.println("각 맵에 1000개 추가");
		for (int i = 0; i < 1000; i++) {
			hashMap.put("" + i, i);
			concurrentHashMap.put("" + i, i);
		}

		System.out.println("각 맵에 1000개 삭제(여러쓰레드로 동시에)");
		System.out.println("============ HashMap ==========");
		test(hashMap);

		System.out.println("============ ConcurrentHashMap ==========");
		test(concurrentHashMap);

	}

	private static void test(final Map<String, Integer> hashMap) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			final int finalI = i;
			service.execute(new Runnable() {
				public void run() {
					hashMap.remove("" + finalI);
				}
			});
		}

		TimeUnit.SECONDS.sleep(1);

		service.shutdown();

		System.out.println("expected map size is '0', test result is '" + hashMap.size() + "'");
	}
}
