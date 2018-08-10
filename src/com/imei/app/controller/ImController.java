package com.imei.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dto.ItemDTO;
import com.imei.app.dto.MessageDTO;
import com.imei.app.dto.MessageListDTO;
import com.imei.app.entity.Message;
import com.imei.app.entity.User;
import com.imei.app.service.MessageService;
import com.imei.app.service.UserService;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/im")
public class ImController {
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	
	@RequestMapping(value ="/getUnreadMsg", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result<ItemDTO> getUnreadMsg(@Param("userId") Long userId){
		User user = userService.findUser(userId);
		if (user==null) {
			return new Result<>(-1, "用户不存在");
		}
		List<Message> msgList = messageService.queryUnreadList(userId);
		HashMap<Long, List<MessageDTO>> map = new HashMap<Long, List<MessageDTO>>();
		for(Message message : msgList) {
			if (!map.containsKey(message.getSenderId())) {
				List<MessageDTO> list = new ArrayList<MessageDTO>();
				MessageDTO dto = new MessageDTO(message.getId(), message.getContent(), message.getSendTime(), message.getSenderId(), message.getRecverId());
				list.add(dto);
				map.put(message.getSenderId(), list);
			}else {
				List<MessageDTO> list = map.get(message.getSenderId());
				MessageDTO dto = new MessageDTO(message.getId(), message.getContent(), message.getSendTime(), message.getSenderId(), message.getRecverId());
				list.add(dto);
				map.put(message.getSenderId(), list);
			}
		}
		List<MessageListDTO> datas = new ArrayList<MessageListDTO>();
		for(Entry<Long, List<MessageDTO>> entry:map.entrySet()) {
			MessageListDTO dto = new MessageListDTO();
			dto.setSenderId(entry.getKey());
			dto.setMsgList(entry.getValue());
			datas.add(dto);
		}
		
		return new Result(0, "success", datas);
	}
	
	
	@RequestMapping(value ="/setMsgReaded", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result setMsgReaded(@Param("msgId")String msgId) {
		if (msgId==null||msgId.trim().equals("")) {
			return new Result<>(-1, "msgId不能为空");
		}
		String[] msgIdArray = msgId.split(",");
		for(String id : msgIdArray) {
			messageService.updateMessageReadStatus(Long.parseLong(id),1);
		}
		return new Result<>(0, "success");
	}
}
