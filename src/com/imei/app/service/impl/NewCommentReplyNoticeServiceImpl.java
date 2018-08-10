package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.NewCommentReplyNoticeDao;
import com.imei.app.entity.NewCommentReplyNotice;
import com.imei.app.service.NewCommentReplyNoticeService;

@Service
public class NewCommentReplyNoticeServiceImpl implements NewCommentReplyNoticeService {
	@Autowired
	NewCommentReplyNoticeDao noticeDao;
	@Override
	public int save(NewCommentReplyNotice notice) {
		// TODO Auto-generated method stub
		return noticeDao.save(notice);
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return noticeDao.delete(id);
	}

	@Override
	public List<NewCommentReplyNotice> queryAll(long userId) {
		// TODO Auto-generated method stub
		return noticeDao.queryAll(userId);
	}

}
