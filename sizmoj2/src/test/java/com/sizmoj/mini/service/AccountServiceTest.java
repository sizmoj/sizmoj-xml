package com.sizmoj.mini.service;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class AccountServiceTest {

	@Test
	public void testGetUser() {
		System.out.println(FileUtils.getUserDirectoryPath().toString());
		System.out.println(FileUtils.getUserDirectory().toString());
	}
}