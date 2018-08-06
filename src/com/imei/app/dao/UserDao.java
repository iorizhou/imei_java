package com.imei.app.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.User;

public interface UserDao {
	long regUser(User user);
}
