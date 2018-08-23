package com.lanqiao.qq.entity;

import java.io.Serializable;

public class AddRSMsg implements Serializable{
	private User form;
	private User to;
	private boolean agree;
	public User getForm() {
		return form;
	}
	public void setForm(User form) {
		this.form = form;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public boolean isAgree() {
		return agree;
	}
	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	
	

}
