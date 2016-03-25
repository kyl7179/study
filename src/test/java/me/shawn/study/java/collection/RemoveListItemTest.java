package me.shawn.study.java.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @link http://bestalign.github.io/2015/08/31/top-10-mistakes-java-developers-make-1/
 * 
 * @author shawn.ctl
 */
public class RemoveListItemTest {
	private final ArrayList<String> testList = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));

	@Test
	public void wrongMethod() {
		// remove b,c
		for (int i = 0; i < testList.size(); i++) {
			if (i == 1 || i == 2)
				testList.remove(i);
		}

		// remain c
		assertTrue(testList.contains("c"));
	}

	@Test(expected = ConcurrentModificationException.class)
	public void wrongMethodWithException() {
		for (String s : testList) {
			if (s.equals("b") || s.equals("c"))
				testList.remove(s);
		}

	}

	@Test
	public void goodMethod() {
		// remove b,c
		Iterator<String> iter = testList.iterator();
		while (iter.hasNext()) {
			String s = iter.next();
			if (s.equals("b") || s.equals("c"))
				iter.remove();
		}

		assertFalse(testList.contains("b"));
		assertFalse(testList.contains("c"));
	}
}
