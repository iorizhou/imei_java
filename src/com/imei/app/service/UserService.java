package com.imei.app.service;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.imei.app.entity.User;

public interface UserService {
	long regUser(String phoneNum,String pwd,String nickName,Date regDate,String city);
	User findUser(long id);
	int checkPhonenumReg(String phoneNum);
	User login(String phoneNum,String pwd);
	public int updateUserInfo(User user);
	
}
