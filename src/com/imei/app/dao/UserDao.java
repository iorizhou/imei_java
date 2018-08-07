package com.imei.app.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.User;

public interface UserDao {
	int regUser(User user);
	User findUser(@Param("id")long id);
	int checkPhonenumReg(@Param("phone_num")String phoneNum);
	User login(@Param("phoneNum")String phoneNum,@Param("pwd")String pwd);
}
