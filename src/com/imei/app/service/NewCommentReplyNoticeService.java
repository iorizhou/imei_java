package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.NewCommentReplyNotice;

public interface NewCommentReplyNoticeService {
	int save(@Param("notice")NewCommentReplyNotice notice);
	int delete(@Param("id")long id);
	List<NewCommentReplyNotice> queryAll(long userId);
}
