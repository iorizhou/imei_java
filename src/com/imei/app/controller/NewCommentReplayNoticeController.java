package com.imei.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.imei.app.dto.NewCommentReplyNoticeDTO;
import com.imei.app.entity.NewCommentReplyNotice;
import com.imei.app.entity.User;
import com.imei.app.service.DiaryService;
import com.imei.app.service.NewCommentReplyNoticeService;
import com.imei.app.service.UserService;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/newcommentreplynotice")
public class NewCommentReplayNoticeController {
	@Autowired
	NewCommentReplyNoticeService noticeService;
	@Autowired
	DiaryService diaryService;
	@Autowired
	UserService userService;
	@RequestMapping(value ="/getNewNoticeList", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result getNewNoticeList(@Param("userId")long userId) {
		User user = userService.findUser(userId);
		if (user==null) {
			return new Result<>(-1, "userId不合法");
		}
		List<NewCommentReplyNotice> list = noticeService.queryAll(userId);
		List<NewCommentReplyNoticeDTO> datas = new ArrayList<NewCommentReplyNoticeDTO>();
		for(NewCommentReplyNotice notice : list) {
			NewCommentReplyNoticeDTO  dto = new NewCommentReplyNoticeDTO(notice.getId(), notice.getNewReply(), notice.getOriReply(), notice.getReplyTime(), notice.getReplyUserId(), notice.getOrigUserId(), notice.getDiaryId());
			datas.add(dto);
		}
		
		return new Result<>(0, "success",datas);
	}
	
	@RequestMapping(value ="/delete", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result delete(@Param("id")long id) {
		noticeService.delete(id);
		return new Result<>(0, "success");
	}
}
