package com.imei.app.service.impl;

import java.util.Date;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.UserDao;
import com.imei.app.entity.User;
import com.imei.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Override
	public long regUser(String phoneNum, String pwd, String nickName, Date regDate,String city) {
		// TODO Auto-generated method stub
		User user = new User(phoneNum, pwd, nickName, regDate, city);
		return userDao.regUser(user);
	}

}
