package com.imei.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class DateUtil {
	public static String getNowStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}
	
	public static boolean isNowAvailable(String startDate,String endDate) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = simpleDateFormat.parse(startDate);
			Date end = simpleDateFormat.parse(endDate);
			Date now = new Date();
			return (now.before(end)&&now.after(start));
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
