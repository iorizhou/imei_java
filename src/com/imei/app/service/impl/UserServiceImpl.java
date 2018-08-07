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
		userDao.regUser(user);
		System.out.println("service id = "+user.getId() +" pwd = "+user.getPwd());
		return user.getId();
	}

	@Override
	public User findUser(long id) {
		// TODO Auto-generated method stub
		return userDao.findUser(id);
	}

	@Override
	public int checkPhonenumReg(String phoneNum) {
		// TODO Auto-generated method stub
		return userDao.checkPhonenumReg(phoneNum);
	}

	@Override
	public User login(String phoneNum, String pwd) {
		// TODO Auto-generated method stub
		return userDao.login(phoneNum, pwd);
	}
	
	@Override
	public int updateUserInfo(User user) {
		return userDao.updateUserInfo(user);
	}; 
	
}
