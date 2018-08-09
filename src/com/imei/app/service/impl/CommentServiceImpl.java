package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.CommentDao;
import com.imei.app.entity.Comment;
import com.imei.app.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentDao commentDao;
	@Override
	public List<Comment> queryCommentListByDiaryId(long diaryid,int index,int count) {
		// TODO Auto-generated method stub
		return commentDao.queryCommentListByDiaryId(diaryid,index,count);
	}
	
	
}
