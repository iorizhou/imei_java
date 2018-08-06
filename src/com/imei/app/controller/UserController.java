package com.imei.app.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dto.UserDTO;
import com.imei.app.entity.User;
import com.imei.app.service.UserService;
import com.imei.app.util.MD5Util;
import com.imei.app.util.Result;
import com.imei.app.util.TokenUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value ="/reg", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result reg(@RequestParam("phoneNum")String phoneNum,@RequestParam("pwd")String pwd,@RequestParam("city")String city) {
        
		long id = userService.regUser(phoneNum, MD5Util.md5(pwd), "i美er_"+System.currentTimeMillis()+new Random().nextInt(100), new Date(), city);
//		if (user==null) {
//			return new Result<>(-1, "注册失败,该手机号已存在");
//		}
//		UserDTO userDTO = new UserDTO(user.getId(), user.getPhoneNum(), TokenUtil.getInstance().genToken(user.getPhoneNum(), user.getId()), user.getNickName(), user.getRegDate(), user.getCity());
        return new Result(0,"success",id);
	}
}
