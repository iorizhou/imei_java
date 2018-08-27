package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Message;

public interface MessageDao {
	int save(Message msg);
	List<Message> queryUnreadList(@Param("recver_id")long recverId);
	int updateMessageReadStatus(@Param("msg_id")long msgId,@Param("status")int status);
}
