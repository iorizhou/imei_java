package com.imei.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.imei.app.dto.OrderDTO;
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
import com.imei.app.util.Constants;
import com.imei.app.util.DateUtil;
import com.imei.app.util.Result;

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
	
	@RequestMapping(value ="/detail", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result detail(@Param("userId")long userId,@Param("orderId")long orderId) {
		Order order = orderService.queryByIdWithUserId(orderId, userId);
		if (order==null) {
			return new Result(-1, "订单不存在");
		}
		OrderDTO dto = new OrderDTO();
		dto.setBuyCount(order.getBuyCount());
		dto.setConsumeCode(order.getConsumeCode());
		dto.setConsumeUserId(order.getConsumeUserId());
		dto.setCreateDate(order.getCreateDate());
		dto.setDjDiscount(order.getDjDiscount());
		dto.setDjRedPacketId(order.getDjRedPacketId());
		dto.setDjTotalCount(order.getDjTotalCount());
		dto.setId(order.getId());
		dto.setItemId(order.getItemId());
		dto.setItemName(order.getItemName());
		dto.setMessage(order.getMessage());
		dto.setNeedPayCount(order.getNeedPayCount());
		dto.setOrderInvalidTime(order.getOrderInvalidTime());
		dto.setOrderStatus(order.getOrderStatus());
		dto.setPayChannel(order.getPayChannel());
		dto.setPayedCount(order.getPayedCount());
		dto.setPayOrderId(order.getPayOrderId());
		dto.setPayStatus(order.getPayStatus());
		dto.setPhoneNum(order.getPhoneNum());
		dto.setTotalPrice(order.getTotalPrice());
		dto.setUserId(order.getUserId());
		dto.setWkCount(order.getWkCount());
		dto.setWkRedPacketId(order.getWkRedPacketId());
		dto.setYyRedPacketId(order.getYyRedPacketId());
		return new Result(0,"success",dto);
	}
	
	@RequestMapping(value ="/list", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result list(@Param("userId")long userId,@Param("status")int status) {
		List<Order> list = new ArrayList<Order>();
		if (status==-1) {
			//查全部
			list = orderService.queryListByUserId(userId);
		}else {
			list = orderService.queryListByUserIdWithStatus(userId, status);
		}
		if (list==null || list.size()==0) {
			return new Result(-1, "订单列表为空");
		}
		List<OrderDTO> datas = new ArrayList<>();
		for(Order order : list) {
			OrderDTO dto = new OrderDTO();
			dto.setBuyCount(order.getBuyCount());
			dto.setConsumeCode(order.getConsumeCode());
			dto.setConsumeUserId(order.getConsumeUserId());
			dto.setCreateDate(order.getCreateDate());
			dto.setDjDiscount(order.getDjDiscount());
			dto.setDjRedPacketId(order.getDjRedPacketId());
			dto.setDjTotalCount(order.getDjTotalCount());
			dto.setId(order.getId());
			dto.setItemId(order.getItemId());
			dto.setItemName(order.getItemName());
			dto.setMessage(order.getMessage());
			dto.setNeedPayCount(order.getNeedPayCount());
			dto.setOrderInvalidTime(order.getOrderInvalidTime());
			dto.setOrderStatus(order.getOrderStatus());
			dto.setPayChannel(order.getPayChannel());
			dto.setPayedCount(order.getPayedCount());
			dto.setPayOrderId(order.getPayOrderId());
			dto.setPayStatus(order.getPayStatus());
			dto.setPhoneNum(order.getPhoneNum());
			dto.setTotalPrice(order.getTotalPrice());
			dto.setUserId(order.getUserId());
			dto.setWkCount(order.getWkCount());
			dto.setWkRedPacketId(order.getWkRedPacketId());
			dto.setYyRedPacketId(order.getYyRedPacketId());
			datas.add(dto);
		}
		return new Result(0,"success",datas);
	}
	
	@RequestMapping(value ="/payStatus", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result payStatus(@Param("orderId")long orderId,@Param("userId")long userId) {
		Order order = orderService.queryByIdWithUserId(orderId, userId);
		if (order==null) {
			return new Result(-1, "订单不存在");
		}
		OrderDTO dto = new OrderDTO();
		dto.setId(order.getId());
		dto.setOrderStatus(order.getOrderStatus());
		dto.setPayChannel(order.getPayChannel());
		dto.setPayStatus(order.getPayStatus());
		dto.setNeedPayCount(order.getNeedPayCount());
		dto.setPayedCount(order.getPayedCount());
		dto.setUserId(order.getUserId());
		dto.setOrderInvalidTime(order.getOrderInvalidTime());
		dto.setCreateDate(order.getCreateDate());
		return new Result(0, "success",dto);
	}
	
	//获取支付宝订单预支付信息  app在准备支付前，需要来后端拉取支付信息，成功后才会调用支付宝sdk去支付 
	@RequestMapping(value ="/getAlipayInfo", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result getAlipayInfo(@Param("orderId")long orderId,@Param("userId")long userId) {
		Order order = orderService.queryByIdWithUserId(orderId, userId);
		if (order==null) {
			return new Result<>(-1, "订单不存在");
		}
		if (order.getOrderStatus()!=0) {
			return new Result<>(-1, "该笔订单不能完成支付动作");
		}
		if (order.getPayStatus()==1) {
			return new Result<>(-1, "订单已支付");
		}
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIPAY_GATEWAY, Constants.ALIPAY_APPID,Constants.ALIPAY_PRIVATE_KEY,"json","utf-8",Constants.ALIPAY_PUBLIC_KEY, "RSA");
			AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
			AlipayTradeAppPayModel payModel = new AlipayTradeAppPayModel();
			payModel.setOutTradeNo(order.getId()+"");
			payModel.setTimeoutExpress("30m");
			payModel.setTotalAmount(order.getNeedPayCount()+"");
			payModel.setProductCode("QUICK_MSECURITY_PAY");
			payModel.setSubject("i美--"+order.getItemName());
			payModel.setBody("i美服务购买");
			payRequest.setBizModel(payModel);
			payRequest.setNotifyUrl("http://app.imei666.com/order/orderNotify/alipay");
			AlipayTradeAppPayResponse payResponse = alipayClient.sdkExecute(payRequest);
			String alipayInfo = payResponse.getBody();
			return new Result<>(0, "success",alipayInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(-1, "获取支付宝订单预支付信息失败");
		}
	}
	
	
}
