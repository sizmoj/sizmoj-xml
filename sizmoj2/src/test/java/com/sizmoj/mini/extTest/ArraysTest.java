package com.sizmoj.mini.extTest;

import java.util.Arrays;

import org.junit.Test;

public class ArraysTest {
	@Test
	public void testArrays() {
		String[] str = {"zxc", "mnb", "qwe"};
		Arrays.sort(str);
		 System.out.println(Arrays.toString(str));
		 System.out.println(Arrays.binarySearch(str, "fesafsd"));
	}
}
