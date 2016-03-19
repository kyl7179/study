package me.shawn.study.java.threadsafe.pattern;

import java.util.List;

/**
 * @author shawn.ctl
 */
public class Obj {
	private List<String> list;

	public Obj(List<String> list) {
		this.list = list;
	}

	public void printList() {
		System.out.println(list);
	}
}
