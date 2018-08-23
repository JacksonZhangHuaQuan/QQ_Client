package com.lanqiao.qq.biz;


import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.lanqiao.qq.entity.AddFriendsMsg;
import com.lanqiao.qq.entity.AddRSMsg;
import com.lanqiao.qq.entity.Find;
import com.lanqiao.qq.entity.RegeditRS;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.ui.FindRSJFrame;
import com.lanqiao.qq.ui.MainJFrame;
import com.lanqiao.qq.util.DialogUtil;
import com.lanqiao.qq.util.ObjectUtil;

public class SysBiz {

	private Socket s;
	
	public SysBiz(Socket s) {
		this.s = s;
	}

	/*
	 * 验证用户名 密码是否正确
	 * param u
	 * return 返回null代表登录失败，如果返回User（所有信息）
	 */
	public User login(User u) throws IOException, ClassNotFoundException {
		
		//1.把user写进进去 -》 服务端
		ObjectUtil.writeObject(s, u);
		
		//2.读取服务端的验证界面
		return (User) ObjectUtil.readObject(s);
		
	}
	
	
	// 注册 成功返回账户 错误返回提示!!!
	public RegeditRS register(User u) throws IOException, ClassNotFoundException {
		// 1.把user写进进去 -》 服务端
		ObjectUtil.writeObject(s, u);

		// 2.读取服务端的验证界面
		return (RegeditRS) ObjectUtil.readObject(s);
	}
	
	//发送查找好友
	public void find(Find find) throws IOException, ClassNotFoundException {
		
		ObjectUtil.writeObject(s, find);
	}
	
	//显示查找结果
	public void showFindRS(List<User> ulist, User u){
		
		System.out.println("查询结果数量：" + ulist.size());
		if(ulist.size() == 0){//没有查询结果
			DialogUtil.showAlarm("没有查询到任何结果!!!");
		}else{
			FindRSJFrame rs = new FindRSJFrame(ulist, u, s);
			rs.setLocationRelativeTo(null);
			rs.setVisible(true);
		}
	}
	
	//显示添加好友信息
	public void showAddMsg(AddFriendsMsg msg, MainJFrame mj) throws IOException{
		
		//弹确认对话框
		boolean b = DialogUtil.showConfirm(msg.getFrom().getNickname() + "请求添加你为好友，是否同意？");
		
		if(b){//同意 -> 添加头像到主窗口
			System.out.println("同意！！！");
			mj.addFriend(msg.getFrom());
		}else{// 不同意
			System.out.println("不同意！！");
		}
		
		AddRSMsg rs = new AddRSMsg();
		rs.setForm(msg.getTo());
		rs.setTo(msg.getFrom());
		rs.setAgree(b);
		ObjectUtil.writeObject(s, rs);
	}
	
	
	//添加好友的结果
	public void showAddRS(AddRSMsg rs, MainJFrame mj){
		if(rs.isAgree()){
			//同意
			DialogUtil.showInfo(rs.getForm().getNickname() + "同意了你添加好友请求！！");
			mj.addFriend(rs.getForm());
		}else{
			DialogUtil.showAlarm(rs.getForm().getNickname() + "拒绝了你的好友请求！！");
		}
	}
	
	//找回密码
	public User findPass(User u) throws IOException, ClassNotFoundException {
		// 1.把user写进进去 -》 服务端
		ObjectUtil.writeObject(s, u);

		// 2.读取服务端的验证界面
		return (User) ObjectUtil.readObject(s);
	}
}
