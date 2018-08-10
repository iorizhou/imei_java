package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Comment;

public interface CommentService {
	List<Comment> queryCommentListByDiaryId(long diaryid,int index,int count);
	int addComment(Comment comment);
}
