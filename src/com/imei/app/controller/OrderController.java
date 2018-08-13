package com.imei.app.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.entity.Item;
import com.imei.app.entity.Order;
import com.imei.app.entity.SubscriptionRedPacket;
import com.imei.app.entity.WKRedPacket;
import com.imei.app.entity.YYRedPacket;
import com.imei.app.service.HospitalService;
import com.imei.app.service.ItemService;
import com.imei.app.service.OrderService;
import com.imei.app.service.SubscriptionRedPacketService;
import com.imei.app.service.UserService;
import com.imei.app.service.WKRedPacketService;
import com.imei.app.service.YYRedPacketService;
import com.imei.app.util.DateUtil;
import com.imei.app.util.Result;

import jdk.nashorn.internal.runtime.arrays.IntOrLongElements;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	@Autowired
	ItemService itemService;
	@Autowired
	HospitalService hospitalService;
	@Autowired
	SubscriptionRedPacketService djService;
	@Autowired
	WKRedPacketService wkService;
	@Autowired
	YYRedPacketService yyService;
	@RequestMapping(value ="/create", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result create(@Param("itemId")long itemId,@Param("userId")long userId,@Param("phoneNum")String phoneNum,@Param("message")String message,@Param("buyCount")int buyCount,@Param("djRedPacketId")long djRedPacketId,@Param("wkRedPacketId")long wkRedPacketId,@Param("yyRedPacketId")long yyRedPacketId,@Param("payChannel")int payChannel) {
//		int orderId = orderService.create(order)
		Item item = itemService.queryById(itemId);
		if (item==null) {
			return new Result(-1,"项目id错误 项目不存在");
		}
		//计算总价
		long totalPrice = item.getDiscountPrice() * buyCount;
		//计算订金总价
		long totalDj = item.getDjCount() * buyCount;
		//查询订金优惠额 
		long djDiscount = 0;
		SubscriptionRedPacket djPacket = djService.queryById(djRedPacketId,userId);
		if (djPacket!=null&&item.getDiscountPrice()>=djPacket.getConditionAmount()&&djPacket.getStatus()==0&&DateUtil.isNowAvailable(djPacket.getStartDate(), djPacket.getEndDate())) {
			if (item.getDiscountPrice()>=djPacket.getConditionAmount()) {
				//满足红包使用条件
				djDiscount = djPacket.getAmount();
			}
		}
		//计算尾款红包优惠
		long wkYouhui = 0;
		WKRedPacket wkRedPacket = wkService.queryById(wkRedPacketId, userId);
		if (wkRedPacket!=null&&item.getDiscountPrice()>=wkRedPacket.getConditionAmount()&&wkRedPacket.getStatus()==0&&DateUtil.isNowAvailable(wkRedPacket.getStartDate(), wkRedPacket.getEndDate())) {
			wkYouhui = wkRedPacket.getAmount();
		}
		//计算医院红包
		long yyPacket = 0;
		YYRedPacket yyRedPacket = yyService.queryById(yyRedPacketId, userId);
		if (yyRedPacket!=null&&item.getDiscountPrice()>=yyRedPacket.getConditionAmount()&&yyRedPacket.getStatus()==0&&DateUtil.isNowAvailable(yyRedPacket.getStartDate(), yyRedPacket.getEndDate())) {
			yyPacket = yyRedPacket.getAmount();
		}
		//得到最终的尾款金额 总价 = 定金 - 尾款优惠 - 医院红包
		long wkCount = totalPrice - totalDj - wkYouhui - yyPacket; 
		long needPayCount = totalDj - djDiscount;
		Order order = new Order(item.getName(), itemId, phoneNum, message, totalPrice, buyCount, djRedPacketId, wkRedPacketId, yyRedPacketId, totalDj, djDiscount, wkCount, 0, "", payChannel, 0, DateUtil.getDateAfter(30*60*1000), 0, "", userId, 0,needPayCount,DateUtil.getNowStr());
		int orderId = orderService.create(order);
		if (orderId<=0) {
			return new Result(-1,"订单创建失败，请稍候重试");
		}
		return new Result(0, "success",orderId);
	}
}
