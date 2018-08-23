package com.lanqiao.qq.entity;

import java.io.Serializable;

public class AddFriendsMsg implements Serializable{
	
	private User from;//谁要求添加好友
	private User to;//要添加谁为好友
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	

}
