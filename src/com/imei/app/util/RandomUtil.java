package com.imei.app.util;

import java.util.Random;

public class RandomUtil {
	
	
	public static String genRandomWithChar(int length) {
		String[] random = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		String result ="";
		Random randomUtil = new Random();
		for (int i = 0; i < length; i++) {
			result +=random[randomUtil.nextInt(random.length)];
		}
		return result;
		
	}
}
