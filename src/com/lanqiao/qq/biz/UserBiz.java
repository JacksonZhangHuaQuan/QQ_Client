package com.lanqiao.qq.biz;

import java.io.IOException;
import java.net.Socket;

import com.lanqiao.qq.entity.AddFriendsMsg;
import com.lanqiao.qq.entity.SendFileMsg;
import com.lanqiao.qq.entity.SendFileRs;
import com.lanqiao.qq.entity.SendMsg;
import com.lanqiao.qq.thread.ReceiveFileThread;
import com.lanqiao.qq.thread.SendFileThread;
import com.lanqiao.qq.ui.MainJFrame;
import com.lanqiao.qq.util.DialogUtil;
import com.lanqiao.qq.util.ObjectUtil;

public class UserBiz {

	private Socket s;//链接服务器的Socket
	
	public UserBiz(Socket s) {
		this.s = s;
	}


	public void addFriends(AddFriendsMsg msg) throws IOException{
		ObjectUtil.writeObject(s, msg);//发送添加信息给服务器
	}
	
	//发消息
	public void sedMsg(SendMsg msg) throws IOException{
		ObjectUtil.writeObject(s, msg);//发送信息给服务器
	}	
	
	//显示消息
	public void showMsg(SendMsg msg, MainJFrame mj){
		mj.tip(msg);//显示消息
	}
	
	//发送文件
	public void sendFileMsg(SendFileMsg msg) throws IOException{
		
		ObjectUtil.writeObject(s, msg);
	}
	
	//显示文件信息
	public void showSendFileMsg(SendFileMsg msg) throws IOException{
		boolean b = DialogUtil.showConfirm(msg.getFrom().getNickname()
				+ ",要发送文件:" + msg.getFilename() + " 给你，是否接受？");
		System.out.println("是否接受文件：" + b);
		if(b){//接受文件
			new ReceiveFileThread(msg, s).start();
		}else{//不接受文件
			SendFileRs rs = new SendFileRs();
			rs.setFrom(msg.getFrom());
			rs.setTo(msg.getTo());
			rs.setAgreed(false);
			ObjectUtil.writeObject(s, rs);
		}
	}
	
	public void showSendFileRS(SendFileRs rs){
		if(rs.isAgreed()){//同意
			//-->线程 --> 链接Socket --> 发送文件
			new SendFileThread(rs).start();
		}else{
			DialogUtil.showInfo(rs.getFrom().getNickname() + "拒绝接受你的文件");
		}
	}
}
