package com.imei.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dto.DiaryDTO;
import com.imei.app.dto.SubscriptionRedPacketDTO;
import com.imei.app.entity.Diary;
import com.imei.app.entity.SubscriptionRedPacket;
import com.imei.app.service.SubscriptionRedPacketService;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/djRedPacket")
public class SubscriptionRedPacketController {
	@Autowired
	SubscriptionRedPacketService redService;

	// 根据状态查询订金红包列表 0未使用 1已使用 2已过期
	@RequestMapping(value = "/getDJRedPacketList", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getDJRedPacketListByStatus(@Param("userId") long userId,@Param("status")int status) {
		List<SubscriptionRedPacket> list = redService.queryListByUserId(userId, status);
		if (list==null||list.size()==0) {
			return new Result(-1, "该用户无该类状态订金红包");
		}
		List<SubscriptionRedPacketDTO> datas = new ArrayList<SubscriptionRedPacketDTO>();
		for(SubscriptionRedPacket packet : list) {
			SubscriptionRedPacketDTO dto = new SubscriptionRedPacketDTO(packet.getId(), packet.getName(), packet.getUseCondition(), packet.getUserId(), packet.getStartDate(), packet.getEndDate(), packet.getSource(), packet.getAmount(), packet.getConditionAmount(), packet.getStatus());
			datas.add(dto);
		}
		return new Result(0, "success", datas);
	}
}
