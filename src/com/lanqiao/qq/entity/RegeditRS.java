package com.lanqiao.qq.entity;

import java.io.Serializable;

public class RegeditRS implements Serializable{

	private boolean rs;//注册成功还是失败
	private String msg;//注册结果的消息
	
	public boolean isRs() {
		return rs;
	}
	public void setRs(boolean rs) {
		this.rs = rs;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
