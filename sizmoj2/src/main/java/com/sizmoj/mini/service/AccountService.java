package com.sizmoj.mini.service;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;
import org.springside.modules.utils.PropertiesLoader;

@Service
public class AccountService {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final Properties p = new PropertiesLoader("classpath:/app.properties").getProperties();
	
	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	/**
	 *	验证成功返回用户名
	 **/
	public String getUser(String username, String password) {
		if(!StringUtils.equals(p.getProperty("user.username"), username))
			return null;
		if(!StringUtils.equals(password, p.getProperty("user.password")))
			return null;
		return username;
	}
	/**
	 *	验证成功返回用户名
	 **/
	public String getUser() {
		return p.getProperty("user.username");
	}
}
