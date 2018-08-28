package com.imei.app.util;

import org.json.JSONObject;

import com.imei.app.entity.Message;
import com.imei.app.entity.PushToken;
import com.tencent.xinge.XingeApp;

public class MessagePushUtil {
	public static MessagePushUtil sInstance;
	private XingeApp xingeApp;
	private MessagePushUtil() {
		if (xingeApp==null) {
			xingeApp = new XingeApp(2100301989, "b45cb6a08853ce9b1aeff9fa9c0ea498");
		}
		
	}
	
	public static synchronized MessagePushUtil getInstance() {
		if (sInstance==null) {
			sInstance = new MessagePushUtil();
		}
		return sInstance;
	}
	
	public JSONObject pushSingleMessage(Message imeiMessage,PushToken pushToken) {
		if (pushToken.getDeviceType()==1) {
			//ios
			return pushIOSSingleMessage(imeiMessage, pushToken);
		}else {
			return pushAndroidSingleMessage(imeiMessage, pushToken);
		}
	}
	
	private JSONObject pushAndroidSingleMessage(Message imeiMessage,PushToken pushToken) {
		String contentJson = com.alibaba.fastjson.JSONObject.toJSONString(imeiMessage);
		System.out.println(contentJson);
		com.tencent.xinge.Message xingeMessage = new com.tencent.xinge.Message();
		xingeMessage.setTitle(""+imeiMessage.getMessageType());
		xingeMessage.setContent(contentJson);
		xingeMessage.setType(com.tencent.xinge.Message.TYPE_MESSAGE);
		JSONObject jsonObject = xingeApp.pushSingleDevice(pushToken.getPushToken(), xingeMessage);
		return jsonObject;
	}
	
	private JSONObject pushIOSSingleMessage(Message imeiMessage,PushToken pushToken) {
		String contentJson = com.alibaba.fastjson.JSONObject.toJSONString(imeiMessage);
		System.out.println(contentJson);
		JSONObject jsonObject = XingeApp.pushTokenIos(2100301989, "b45cb6a08853ce9b1aeff9fa9c0ea498", contentJson, pushToken.getPushToken(), XingeApp.IOSENV_PROD);
		return jsonObject;
	}
}
