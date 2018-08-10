package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.NewCommentReplyNotice;

public interface NewCommentReplyNoticeDao {
	int save(@Param("notice")NewCommentReplyNotice notice);
	int delete(@Param("id")long id);
	List<NewCommentReplyNotice> queryAll(@Param("userId")long userId);
}
