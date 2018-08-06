package com.imei.app.util;

import static org.junit.Assert.assertNotNull;

import java.util.Base64;

public class TokenUtil {
	
	public static TokenUtil sInstance;
	
	private TokenUtil() {
		
	}
	
	public synchronized static TokenUtil getInstance() {
		if (sInstance==null) {
			sInstance = new TokenUtil();
		}
		return sInstance;
	}
	
	public String genToken(String phoneNum,long id) {
		String result = null;
		String str1 = id+phoneNum+RandomUtil.genRandomWithChar(5);
		String random = RandomUtil.genRandomWithChar(10);
		try {
			result = Base64.getEncoder().encodeToString(str1.getBytes("utf-8"));
			result = MD5Util.md5(result);
			result = result+random;
		}catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
}
