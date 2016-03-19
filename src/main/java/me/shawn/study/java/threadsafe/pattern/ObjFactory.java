package me.shawn.study.java.threadsafe.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn.ctl
 */
public class ObjFactory {
	public static Obj create() {
		List<String> list = new ArrayList<String>();
		Obj obj = new Obj(list);
		return obj;
	}
}
