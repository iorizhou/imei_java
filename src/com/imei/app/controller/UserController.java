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
		int count = userService.checkPhonenumReg(phoneNum);
		if (count>0) {
			return new Result<>(-1, "ע��ʧ��,���ֻ����Ѵ���");
		}
		long id = userService.regUser(phoneNum, MD5Util.md5(pwd), "i��er_"+System.currentTimeMillis()+new Random().nextInt(100), new Date(), city);
		User user =userService.findUser(id);
		System.out.println("userid = "+user.getId());
		if (user==null) {
			return new Result<>(-1, "ע��ʧ��");
		}
		UserDTO userDTO = new UserDTO(user.getPhoneNum(), TokenUtil.getInstance().genToken(user.getPhoneNum(), user.getId()), user.getNickName(), user.getRegDate(), user.getCity());
        return new Result(0,"success",userDTO);
	}
	
	@RequestMapping(value ="/login", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result login(@RequestParam("phoneNum")String phoneNum,@RequestParam("pwd")String pwd) {
		User user = userService.login(phoneNum, MD5Util.md5(pwd));
		if (user==null) {
			return new Result<>(-1, "��¼ʧ�ܣ����������ʺ�����");
		}
		UserDTO userDTO = new UserDTO(user.getPhoneNum(), TokenUtil.getInstance().genToken(user.getPhoneNum(), user.getId()), user.getNickName(), user.getRegDate(), user.getCity());
        return new Result(0,"success",userDTO);
	}
	
	@RequestMapping(value ="/updateAccountInfo", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result updateAccountInfo(@RequestParam("userId")long id,@RequestParam("city")String city) {
		System.out.println("city = "+city);
		User user = userService.findUser(id);
		if (user==null) {
			return new Result<>(-1, "�޸�ʧ�ܣ����û�������");
		}
		user.setCity(city);
		int count = userService.updateUserInfo(user);
		
		if (count<=0) {
			return new Result<>(-1, "�޸�ʧ�ܣ����Ժ�����");
		}
        return new Result(0,"������Ϣ�޸ĳɹ�");
	}
}
