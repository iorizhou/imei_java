package com.imei.app.util;

public class Result<T> {
	private int msgCode;
	private String msg;
	private T datas;
	public int getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(int msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getDatas() {
		return datas;
	}
	public void setDatas(T datas) {
		this.datas = datas;
	}
	public Result(int msgCode, String msg, T datas) {
		super();
		this.msgCode = msgCode;
		this.msg = msg;
		this.datas = datas;
	}
	public Result(int msgCode, String msg) {
		super();
		this.msgCode = msgCode;
		this.msg = msg;
	}
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "dddcdd";
	}
	
	
	

}
