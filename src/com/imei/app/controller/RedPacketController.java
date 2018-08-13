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
import com.imei.app.dto.WKRedPacketDTO;
import com.imei.app.dto.YYRedPacketDTO;
import com.imei.app.entity.Diary;
import com.imei.app.entity.SubscriptionRedPacket;
import com.imei.app.entity.WKRedPacket;
import com.imei.app.entity.YYRedPacket;
import com.imei.app.service.SubscriptionRedPacketService;
import com.imei.app.service.WKRedPacketService;
import com.imei.app.service.YYRedPacketService;
import com.imei.app.util.DateUtil;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/redpacket")
public class RedPacketController {
	@Autowired
	SubscriptionRedPacketService redService;
	@Autowired
	WKRedPacketService wkRedPacketService;
	@Autowired
	YYRedPacketService yyRedPacketService;
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
			if (status==0) {
				//如果是查未使用的，则需要判断是否在有效期内
				if (DateUtil.isNowAvailable(packet.getStartDate(), packet.getEndDate())) {
					SubscriptionRedPacketDTO dto = new SubscriptionRedPacketDTO(packet.getId(), packet.getName(), packet.getUseCondition(), packet.getUserId(), packet.getStartDate(), packet.getEndDate(), packet.getSource(), packet.getAmount(), packet.getConditionAmount(), packet.getStatus());
					datas.add(dto);
				}
			} else {
				SubscriptionRedPacketDTO dto = new SubscriptionRedPacketDTO(packet.getId(), packet.getName(),
						packet.getUseCondition(), packet.getUserId(), packet.getStartDate(), packet.getEndDate(),
						packet.getSource(), packet.getAmount(), packet.getConditionAmount(), packet.getStatus());
				datas.add(dto);
			}
		}
		return new Result(0, "success", datas);
	}
	
	// 查询尾款红包列表
	@RequestMapping(value = "/getWKRedPacketList", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getWKRedPacketList(@Param("userId") long userId) {
		List<WKRedPacket> list = wkRedPacketService.queryListByUserId(userId, 0);
		if (list == null || list.size() == 0) {
			return new Result(-1, "该用户无可用尾款红包");
		}
		List<WKRedPacketDTO> datas = new ArrayList<WKRedPacketDTO>();
		for (WKRedPacket packet : list) {
			if (DateUtil.isNowAvailable(packet.getStartDate(), packet.getEndDate())) {
				WKRedPacketDTO dto = new WKRedPacketDTO(packet.getId(), packet.getName(),
						packet.getUseCondition(), packet.getUserId(), packet.getStartDate(), packet.getEndDate(),
						packet.getSource(), packet.getAmount(), packet.getConditionAmount(), packet.getStatus());
				datas.add(dto);
			}
		}
		return new Result(0, "success", datas);
	}
	
	// 查询尾款红包列表
	@RequestMapping(value = "/getYYRedPacketList", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getYYRedPacketList(@Param("userId") long userId) {
		List<YYRedPacket> list = yyRedPacketService.queryListByUserId(userId, 0);
		if (list == null || list.size() == 0) {
			return new Result(-1, "该用户无可用医院红包");
		}
		List<YYRedPacketDTO> datas = new ArrayList<YYRedPacketDTO>();
		for (YYRedPacket packet : list) {
			if (DateUtil.isNowAvailable(packet.getStartDate(), packet.getEndDate())) {
				YYRedPacketDTO dto = new YYRedPacketDTO(packet.getId(), packet.getName(), packet.getUseCondition(),
						packet.getUserId(), packet.getStartDate(), packet.getEndDate(), packet.getSource(),
						packet.getAmount(), packet.getConditionAmount(), packet.getStatus(), packet.getHospitalId());
				datas.add(dto);
			}
		}
		return new Result(0, "success", datas);
	}
}
