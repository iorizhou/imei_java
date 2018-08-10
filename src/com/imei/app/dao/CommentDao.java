package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Comment;

public interface CommentDao {
	List<Comment> queryCommentListByDiaryId(@Param("diary_id")long diaryid,@Param("index")int index,@Param("count")int count);
	int addComment(Comment comment);
}
