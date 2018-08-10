package com.imei.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dao.CommentDao;
import com.imei.app.dto.CommentDTO;
import com.imei.app.dto.DiaryDTO;
import com.imei.app.dto.DiaryItemDTO;
import com.imei.app.dto.DiaryTypeDTO;
import com.imei.app.dto.ItemDTO;
import com.imei.app.entity.Comment;
import com.imei.app.entity.Diary;
import com.imei.app.entity.DiaryItem;
import com.imei.app.entity.DiaryType;
import com.imei.app.entity.Item;
import com.imei.app.entity.User;
import com.imei.app.service.CommentService;
import com.imei.app.service.DiaryItemService;
import com.imei.app.service.DiaryService;
import com.imei.app.service.DiaryTypeService;
import com.imei.app.service.ItemService;
import com.imei.app.service.UserService;
import com.imei.app.util.DateUtil;
import com.imei.app.util.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/diary")
public class DiaryController {

	@Autowired
	DiaryTypeService diaryTypeService;
	@Autowired
	DiaryService diaryService;
	@Autowired
	DiaryItemService diaryItemService;
	@Autowired
	ItemService itemService;
	@Autowired
	CommentService commentService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/getAllDiaryTypes", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getAllDiaryTypes() {
		List<DiaryType> lists = diaryTypeService.queryAllDiaryTypes();
		if (lists == null || lists.size() == 0) {
			return new Result<ItemDTO>(0, "���������ռǷ�����Ϣ");
		}
		List<DiaryTypeDTO> datas = new ArrayList<DiaryTypeDTO>();
		for (DiaryType type : lists) {
			DiaryTypeDTO dto = new DiaryTypeDTO(type.getId(), type.getName(), type.getSortOrder());
			datas.add(dto);
		}
		return new Result(0, "success", datas);
	}

	@RequestMapping(value = "/getDiaryListByType", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getDiaryListByType(@RequestParam("typeid") long typeid, @RequestParam("index") int index,
			@RequestParam("count") int count) {
		System.out.println("typeid= " + typeid + " index = " + index + " count = " + count);
		List<Diary> lists = diaryService.getDiaryListByType(typeid, index, count);
		if (lists == null || lists.size() == 0) {
			return new Result<ItemDTO>(0, "�÷��������������ռ�");
		}
		List<DiaryDTO> datas = new ArrayList<DiaryDTO>();
		for (Diary diary : lists) {
			DiaryDTO dto = new DiaryDTO(diary.getId(), diary.getAuthor(), diary.getPublishTime(),
					diary.getAuthorAvatar(), diary.getCoverImg(), diary.getSimpleContent(), diary.getTag(),
					diary.getItemId(), diary.getItemName(), diary.getItemDiscount(), diary.getViewCount(),
					diary.getCommentCount(), diary.getDiaryTypeId());
			datas.add(dto);
		}
		return new Result(0, "success", datas);
	}

	// 根据日记id，查询子item列表
	@RequestMapping(value = "/getDiaryItemListByDiaryId", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getDiaryItemListByDiaryId(@RequestParam("diaryid") long diaryid, @RequestParam("index") int index,
			@RequestParam("count") int count) {
		List<DiaryItem> lists = diaryItemService.queryListByDiaryId(diaryid, index, count);
		if (lists == null || lists.size() == 0) {
			return new Result<ItemDTO>(0, "该日记下无详细数据");
		}
		List<DiaryItemDTO> datas = new ArrayList<DiaryItemDTO>();
		for (DiaryItem item : lists) {
			DiaryItemDTO dto = new DiaryItemDTO(item.getId(), item.getDiaryId(), item.getTitle(), item.getContent(),
					item.getPublishDate(), item.getContent());
			datas.add(dto);
		}
		return new Result(0, "success", datas);
	}

	// 根据日记id，查询子item列表
	@RequestMapping(value = "/diaryItemDetail", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result diaryItemDetail(@Param("diaryitemid") long diaryitemid) {
		DiaryItem diaryItem = diaryItemService.queryById(diaryitemid);
		if (diaryItem == null) {
			return new Result(0, "日记项不存在");
		}
		DiaryItemDTO dto = new DiaryItemDTO(diaryItem.getId(), diaryItem.getDiaryId(), diaryItem.getTitle(),
				diaryItem.getContent(), diaryItem.getPublishDate(), diaryItem.getContent());
		// 查询对应的diary
		Diary diary = diaryService.queryById(diaryItem.getDiaryId());
		if (diary != null) {
			// 查询对应的项目
			Item item = itemService.queryById(diary.getItemId());
			if (item != null) {
				dto.setItemDiscount(item.getDiscountPrice());
				dto.setItemId(item.getId());
				dto.setItemName(item.getName());
				dto.setItemOrigPrice(item.getOrigPrice());
				dto.setItemPicUrl(item.getCover());
			}
		}
		return new Result(0, "success", dto);
	}

	// 根据日记id，查询评论列表
	@RequestMapping(value = "/listComment", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result listComment(@Param("diaryid") long diaryid, @Param("index") int index, @Param("count") int count) {
		List<Comment> list = commentService.queryCommentListByDiaryId(diaryid, index, count);
		if (list == null || list.size() == 0) {
			return new Result(0, "该日记无评 ");
		}
		List<CommentDTO> datas = new ArrayList<CommentDTO>();
		for (Comment comment : list) {
			CommentDTO dto = new CommentDTO();
			dto.setId(comment.getId());
			dto.setDiaryId(comment.getDiaryId());
			dto.setUserId(comment.getUserId());
			dto.setContent(comment.getContent());
			dto.setPublishDate(comment.getPublishDate());
			User user = userService.findUser(comment.getUserId());
			if (user != null) {
				dto.setAuthorName(user.getNickName());
				dto.setAuthorAvatar(user.getAvatar());
			}
			datas.add(dto);
		}
		return new Result(0, "success", datas);
	}

	// 发布评论
	@RequestMapping(value = "/submitComment", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result submitComment(@Param("diaryid") long diaryid, @Param("content") String content,
			@Param("userId") long userId) {
		Diary diary = diaryService.queryById(diaryid);
		if (diary == null) {
			return new Result(-1, "评论失败，日记id不存在");
		}
		Comment comment = new Comment(diaryid, userId, DateUtil.getNowStr(), content);
		int id = commentService.addComment(comment);
		if (id > 0) {
			return new Result(0, "评论成功");
		} else {
			return new Result(-1, "评论失败,请稍候重试");
		}
	}
	
}
