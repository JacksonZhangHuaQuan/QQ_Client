package com.lanqiao.qq.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.lanqiao.qq.biz.SysBiz;
import com.lanqiao.qq.biz.UserBiz;
import com.lanqiao.qq.entity.AddFriendsMsg;
import com.lanqiao.qq.entity.AddRSMsg;
import com.lanqiao.qq.entity.SendFileMsg;
import com.lanqiao.qq.entity.SendFileRs;
import com.lanqiao.qq.entity.SendMsg;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.ui.MainJFrame;
import com.lanqiao.qq.util.DialogUtil;
import com.lanqiao.qq.util.ObjectUtil;

public class ServerThread extends Thread{

	private Socket s; // 链接服务器的
	private User u;
	private SysBiz sBiz;
	private MainJFrame mj;
	private UserBiz uBiz;
	
	public ServerThread(Socket s, User u, MainJFrame mj){
		this.s = s;
		this.u = u;
		this.mj = mj;
		sBiz = new SysBiz(s);
		uBiz = new UserBiz(s);
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Object o = ObjectUtil.readObject(s);
				System.out.println("服务器发送信息： " + o);
				if(o instanceof List){
					List<User> ulist = (List<User>)o;
					sBiz.showFindRS(ulist, u);
				} else if(o instanceof AddFriendsMsg){
					AddFriendsMsg msg = (AddFriendsMsg)o;
					sBiz.showAddMsg(msg, mj);
				} else if(o instanceof AddRSMsg){
					AddRSMsg rs = (AddRSMsg)o;
					sBiz.showAddRS(rs, mj);
				} else if(o instanceof SendMsg){
					SendMsg msg = (SendMsg)o;
					uBiz.showMsg(msg, mj);
				} else if(o instanceof SendFileMsg){
					SendFileMsg msg = (SendFileMsg)o;
					uBiz.showSendFileMsg(msg);
				} else if(o instanceof SendFileRs){
					SendFileRs rs = (SendFileRs)o;
					uBiz.showSendFileRS(rs);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
