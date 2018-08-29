package com.imei.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.ibatis.annotations.Param;
import org.json.JSONObject;
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
import com.imei.app.entity.PushToken;
import com.imei.app.entity.User;
import com.imei.app.service.MessageService;
import com.imei.app.service.PushTokenService;
import com.imei.app.service.UserService;
import com.imei.app.util.DateUtil;
import com.imei.app.util.MessagePushUtil;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/im")
public class ImController {
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	@Autowired
	PushTokenService pushTokenService;
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
				dto.setStatus(message.getStatus());
				dto.setSenderName(message.getSenderName());
				dto.setRecverName(message.getRecverName());
				dto.setSenderAvatar(message.getSenderAvatar());
				dto.setRecverAvatar(message.getRecverAvatar());
				dto.setReadTime(message.getReadTime());
				list.add(dto);
				map.put(message.getSenderId(), list);
			}else {
				List<MessageDTO> list = map.get(message.getSenderId());
				MessageDTO dto = new MessageDTO(message.getId(), message.getContent(), message.getSendTime(), message.getSenderId(), message.getRecverId());
				dto.setStatus(message.getStatus());
				dto.setSenderName(message.getSenderName());
				dto.setRecverName(message.getRecverName());
				dto.setSenderAvatar(message.getSenderAvatar());
				dto.setRecverAvatar(message.getRecverAvatar());
				dto.setReadTime(message.getReadTime());
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
	
	
	@RequestMapping(value ="/sendSingle", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result sendSingle(@Param("content")String content,@Param("userId")long userId,@Param("recvId")long recvId,@Param("messageType")int messageType) {
		//消息类型 0为文本，1为图片，2为音频，3为视频
		if (content==null||content.trim().equals("")) {
			return new Result<>(-1, "消息内容不能为空");
		}
		if (userId<=0||recvId<=0) {
			return new Result<>(-1, "消息发送及接收方ID非法");
		}
		User sender = userService.findUser(userId);
		if (sender==null) {
			return new Result<>(-1, "senderid非法");
		}
		User recver = userService.findUser(recvId);
		if (recver==null) {
			return new Result<>(-1, "recvId非法");
		}
		try {
			Thread.sleep(10000);
		}catch (Exception e) {
			// TODO: handle exception
		}
		Message message = new Message();
		message.setContent(content);
		message.setSenderName(sender.getNickName());
		message.setRecverName(recver.getNickName());
		message.setSenderAvatar(sender.getAvatar());
		message.setRecverAvatar(recver.getAvatar());
		message.setRecverId(recvId);
		message.setSenderId(userId);
		message.setSendTime(DateUtil.getNowStr());
		message.setStatus(0);
		message.setMessageType(messageType);
		message.setReadTime("");
		// 信鸽推成功了， 才存该数据
		int count = messageService.save(message);
		if (count <= 0) {
			return new Result<>(-1, "消息发送失败，请稍候重试");
		}
		PushToken pushToken = pushTokenService.queryByUserId(recvId);
		if (pushToken!=null) {
			//推消息
			JSONObject jsonObject = MessagePushUtil.getInstance().pushSingleMessage(message, pushToken);
			if (jsonObject.getInt("ret_code")!=0) {
				//失败了 则将数据库中也删除掉
				messageService.delete(message.getId());
				return new Result<>(-1, "消息发送失败，请稍候重试");
			}
		}
		
		return new Result<>(0, "消息发送成功",message);
	}
	
	
	
	//注册腾讯信鸽推送SDK的TOKEN
	@RequestMapping(value ="/regToken", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result regToken(@Param("userId")long userId,@Param("token")String token,@Param("deviceType")int deviceType) {
		
		
		//device type 0为安卓 1为IOS  2为网页
		if (userId<=0||token==null||token.trim().equals("")) {
			return new Result<>(-1, "push token注册失败，参数非法");
		}
		//不管那么多，先删除再说，避免重复注册
		pushTokenService.deleteByUserId(userId);
		PushToken entity = new PushToken();
		entity.setUserId(userId);
		entity.setPushToken(token);
		entity.setDeviceType(deviceType);
		int count = pushTokenService.save(entity);
		if (count<=0) {
			return new Result<>(-1, "push token注册失败");
		}
		return new Result<>(0, "push token注册成功",entity.getId());
	}
}
