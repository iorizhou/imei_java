package com.imei.app.service;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.PushToken;

public interface PushTokenService {
	int save(PushToken pushToken);
	int deleteByUserId(@Param("user_id")long userId);
	PushToken queryById(@Param("id")long id);
	PushToken queryByUserId(@Param("user_id")long userId);
	PushToken queryByToken(@Param("token")String token);
}
