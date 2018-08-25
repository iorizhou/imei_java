package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.WKRedPacket;
import com.imei.app.entity.YYRedPacket;

public interface YYRedPacketService {
	int save(YYRedPacket packet);
	int use(long id,int status);
	List<YYRedPacket> queryListByUserId(long userId,int status,long hospitalId);
	YYRedPacket queryById(long id,long userId);
}
