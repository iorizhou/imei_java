package com.imei.app.controller;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.imei.app.dto.OrderDTO;
import com.imei.app.entity.Hospital;
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
import com.imei.app.util.WXPayUtil;
import com.imei.app.util.XMLUtils;

import ch.qos.logback.classic.gaffer.PropertyUtil;

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

	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result create(@Param("itemId") long itemId, @Param("userId") long userId,
			@Param("phoneNum") String phoneNum, @Param("message") String message, @Param("buyCount") int buyCount,
			@Param("djRedPacketId") long djRedPacketId, @Param("wkRedPacketId") long wkRedPacketId,
			@Param("yyRedPacketId") long yyRedPacketId) {
//		int orderId = orderService.create(order)
		Item item = itemService.queryById(itemId);
		if (item == null) {
			return new Result(-1, "项目id错误 项目不存在");
		}
		// 计算总价
		long totalPrice = item.getDiscountPrice() * buyCount;
		// 计算订金总价
		long totalDj = item.getDjCount() * buyCount;
		// 查询订金优惠额
		long djDiscount = 0;
		SubscriptionRedPacket djPacket = djService.queryById(djRedPacketId, userId);
		if (djPacket != null && item.getDiscountPrice() >= djPacket.getConditionAmount() && djPacket.getStatus() == 0
				&& DateUtil.isNowAvailable(djPacket.getStartDate(), djPacket.getEndDate())) {
			if (item.getDiscountPrice() >= djPacket.getConditionAmount()) {
				// 满足红包使用条件
				djDiscount = djPacket.getAmount();
			}
		}
		// 计算尾款红包优惠
		long wkYouhui = 0;
		WKRedPacket wkRedPacket = wkService.queryById(wkRedPacketId, userId);
		if (wkRedPacket != null && item.getDiscountPrice() >= wkRedPacket.getConditionAmount()
				&& wkRedPacket.getStatus() == 0
				&& DateUtil.isNowAvailable(wkRedPacket.getStartDate(), wkRedPacket.getEndDate())) {
			wkYouhui = wkRedPacket.getAmount();
		}
		// 计算医院红包
		long yyPacket = 0;
		YYRedPacket yyRedPacket = yyService.queryById(yyRedPacketId, userId);
		if (yyRedPacket != null && item.getDiscountPrice() >= yyRedPacket.getConditionAmount()
				&& yyRedPacket.getStatus() == 0
				&& DateUtil.isNowAvailable(yyRedPacket.getStartDate(), yyRedPacket.getEndDate())) {
			yyPacket = yyRedPacket.getAmount();
		}
		// 得到最终的尾款金额 总价 = 定金 - 尾款优惠 - 医院红包
		long wkCount = totalPrice - totalDj - wkYouhui - yyPacket;
		long needPayCount = totalDj - djDiscount;
		Order order = new Order(item.getName(), itemId, phoneNum, message, totalPrice, buyCount, djRedPacketId,
				wkRedPacketId, yyRedPacketId, totalDj, djDiscount, wkCount, 0, "", 0, 0,
				DateUtil.getDateAfter(30 * 60 * 1000), 0, "", userId, 0, needPayCount, DateUtil.getNowStr());
		int count = orderService.create(order);
		if (count <= 0) {
			return new Result(-1, "订单创建失败，请稍候重试");
		}
		return new Result(0, "success", order.getId());
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result detail(@Param("userId") long userId, @Param("orderId") long orderId) {
		Order order = orderService.queryByIdWithUserId(orderId, userId);
		if (order == null) {
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
		Item item = itemService.queryById(order.getItemId());
		if (item!=null) {
			dto.setHospitalId(item.getHospitalId());
			Hospital hospital = hospitalService.queryById(item.getHospitalId());
			if (hospital!=null) {
				dto.setHospitalAddr(hospital.getAddr());
				dto.setHospitalName(hospital.getName());
			}
		}
		return new Result(0, "success", dto);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result list(@Param("userId") long userId, @Param("status") int status) {
		List<Order> list = new ArrayList<Order>();
		if (status == -1) {
			// 查全部
			list = orderService.queryListByUserId(userId);
		} else {
			list = orderService.queryListByUserIdWithStatus(userId, status);
		}
		if (list == null || list.size() == 0) {
			return new Result(-1, "订单列表为空");
		}
		List<OrderDTO> datas = new ArrayList<>();
		for (Order order : list) {
			if (order.getOrderStatus()==0 ) {
				//状态为0，说明是未支付的订单，则需要再次过滤一下是否时间失效
				if (!DateUtil.isNowAvailable(order.getCreateDate(), order.getOrderInvalidTime())) {
					continue;
				}
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
			Item item = itemService.queryById(order.getItemId());
			if (item!=null) {
				dto.setHospitalId(item.getHospitalId());
				Hospital hospital = hospitalService.queryById(item.getHospitalId());
				if (hospital!=null) {
					dto.setHospitalAddr(hospital.getAddr());
					dto.setHospitalName(hospital.getName());
				}
			}
			datas.add(dto);
		}
		return new Result(0, "success", datas);
	}

	@RequestMapping(value = "/payStatus", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	private Result payStatus(@Param("orderId") long orderId, @Param("userId") long userId) {
		Order order = orderService.queryByIdWithUserId(orderId, userId);
		if (order == null) {
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
		Item item = itemService.queryById(order.getItemId());
		if (item!=null) {
			dto.setHospitalId(item.getHospitalId());
			Hospital hospital = hospitalService.queryById(item.getHospitalId());
			if (hospital!=null) {
				dto.setHospitalAddr(hospital.getAddr());
				dto.setHospitalName(hospital.getName());
			}
		}
		return new Result(0, "success", dto);
	}

	// 获取支付宝订单预支付信息 app在准备支付前，需要来后端拉取支付信息，成功后才会调用支付宝sdk去支付
	@RequestMapping(value = "/getAlipayInfo", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getAlipayInfo(@Param("orderId") long orderId, @Param("userId") long userId) {
		Order order = orderService.queryByIdWithUserId(orderId, userId);
		if (order == null) {
			return new Result<>(-1, "订单不存在");
		}
		if (order.getOrderStatus() != 0) {
			return new Result<>(-1, "该笔订单不能完成支付动作");
		}
		if (order.getPayStatus() == 1) {
			return new Result<>(-1, "订单已支付");
		}
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIPAY_GATEWAY, Constants.ALIPAY_APPID,
					Constants.ALIPAY_PRIVATE_KEY, "json", "utf-8", Constants.ALIPAY_PUBLIC_KEY, "RSA");
			AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
			AlipayTradeAppPayModel payModel = new AlipayTradeAppPayModel();
			payModel.setOutTradeNo(order.getId() + "");
			payModel.setTimeoutExpress("30m");
			payModel.setTotalAmount(order.getNeedPayCount() + "");
			payModel.setProductCode("QUICK_MSECURITY_PAY");
			payModel.setSubject("i美--" + order.getItemName());
			payModel.setBody("i美--" + order.getItemName());
			payRequest.setBizModel(payModel);
			payRequest.setNotifyUrl("http://app.imei666.com/order/orderNotify/alipay");
			AlipayTradeAppPayResponse payResponse = alipayClient.sdkExecute(payRequest);
			String alipayInfo = payResponse.getBody();
			return new Result<>(0, "success", alipayInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<>(-1, "获取支付宝订单预支付信息失败");
		}
	}

	// 给支付宝server调用的异步通知接口，以接收订单真实支付状态
	@RequestMapping(value = "/orderNotify/alipay", method = RequestMethod.POST, consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody
	private String alipayNotify(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator<String> iter = paramMap.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = paramMap.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		try {
			if (AlipaySignature.rsaCheckV1(params, Constants.ALIPAY_PUBLIC_KEY, "utf-8")) {
				long orderId = Long.parseLong(params.get("out_trade_no"));
				long totalAmount = Long.parseLong(params.get("total_amount"));
				String payOrderId = params.get("trade_no");
				String appId = params.get("app_id");
				String tradeStatus = params.get("trade_status");
				Order order = orderService.queryById(orderId);
				if (order == null) {
					return "failed";
				}
				if (order.getNeedPayCount() == totalAmount && appId.equals(Constants.ALIPAY_APPID)) {
					if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
						orderService.setOrderPayed(orderId, payOrderId, 0, totalAmount);
						return "success";
					}
				}
			} else {
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	// 微信支付获取预支付信息
	// 获取支付宝订单预支付信息 app在准备支付前，需要来后端拉取支付信息，成功后才会调用支付宝sdk去支付
	@RequestMapping(value = "/getWXpayInfo", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result getWXpayInfo(@Param("orderId") long orderId, @Param("userId") long userId) {
		Order order = orderService.queryByIdWithUserId(orderId, userId);
		if (order == null) {
			return new Result<>(-1, "订单不存在");
		}
		if (order.getOrderStatus() != 0) {
			return new Result<>(-1, "该笔订单不能完成支付动作");
		}
		if (order.getPayStatus() == 1) {
			return new Result<>(-1, "订单已支付");
		}
		try {
			HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String remoteAddr = req.getRemoteAddr();

			SortedMap<Object, Object> parameters = WXPayUtil.getWXPrePayID();
			// 商品信息标题
			parameters.put("body", "i美" + order.getItemName());
			parameters.put("spbill_create_ip", remoteAddr);
			parameters.put("out_trade_no", order.getId()); // 订单id这里我的订单id生成规则是订单id+时间
			parameters.put("total_fee", "1"); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
//             parameters.put("total_fee", order.getNeedPayCount()*100+""); // 上线后，将此代码放开
			String sign = WXPayUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);
			String requestXML = WXPayUtil.getRequestXml(parameters); // 获取xml结果
			String result = WXPayUtil.httpsRequest(Constants.WXPAY_URL, "POST", requestXML);
			SortedMap<Object, Object> parMap = WXPayUtil.startWXPay(result);
			if (parMap != null) {
				return new Result<>(0, "success", JSON.toJSONString(parMap));
			} else {
				return new Result<>(-1, "创建微信支付订单失败");
			}
		} catch (Exception e) {
			return new Result<>(-1, "创建微信支付订单失败");
		}
	}

	// 给微信支付server调用的异步通知接口，以接收订单真实支付状态
	@RequestMapping(value = "/orderNotify/wxpay", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result wxpayNotify(HttpServletRequest request, HttpServletResponse response) {
		String resXml = "";
		try {
			String result = WXPayUtil.reciverWx(request);
			Map<String, String> m = new HashMap<String, String>();// 解析xml成map
			if (m != null && !"".equals(m)) {
				m = XMLUtils.Dom2Map(result);
			}
			// 过滤空 设置 TreeMap
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			Iterator it = m.keySet().iterator();
			while (it.hasNext()) {
				String parameter = (String) it.next();
				String parameterValue = m.get(parameter);
				String v = "";
				if (null != parameterValue) {
					v = parameterValue.trim();
				}
				packageParams.put(parameter, v);
			}
			// 判断签名是否正确

			if (WXPayUtil.isTenpaySign("utf-8", packageParams)) {
				if ("SUCCESS".equals((String) packageParams.get("return_code"))&& "SUCCESS".equals((String) packageParams.get("result_code"))) {
					// 如果返回成功
					String mchId = (String) packageParams.get("mch_id"); // 商户号
					String orderId = (String) packageParams.get("out_trade_no"); // 商户订单号
					String totalFee = (String) packageParams.get("total_fee");
//		                String prepayId = (String)packageParams.get("prepay_id");
					String appid = (String) packageParams.get("appid");
					String payOrderId = (String) packageParams.get("transaction_id"); // 微信支付订单号
					Order order = orderService.queryById(Long.parseLong(orderId));
					if (order == null) {
						resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
								+ "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";

					}
					// 验证商户ID 和 价格 以防止篡改金额
					if (appid.equals(Constants.WXPAY_APPID) && Constants.WXPAY_MCHID
							.equals(mchId) /* && Long.parseLong(total_fee)==order.getNeedPayCount()*100 */) {

						orderService.setOrderPayed(Long.parseLong(orderId), payOrderId, 1, order.getNeedPayCount());
						resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
								+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
					} else {
						resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
								+ "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
					}
				} else {
					resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
							+ "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
				}
			} else {
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
		}
		try {
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
