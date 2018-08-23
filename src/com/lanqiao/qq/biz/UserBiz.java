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

	private Socket s;//���ӷ�������Socket
	
	public UserBiz(Socket s) {
		this.s = s;
	}


	public void addFriends(AddFriendsMsg msg) throws IOException{
		ObjectUtil.writeObject(s, msg);//���������Ϣ��������
	}
	
	//����Ϣ
	public void sedMsg(SendMsg msg) throws IOException{
		ObjectUtil.writeObject(s, msg);//������Ϣ��������
	}	
	
	//��ʾ��Ϣ
	public void showMsg(SendMsg msg, MainJFrame mj){
		mj.tip(msg);//��ʾ��Ϣ
	}
	
	//�����ļ�
	public void sendFileMsg(SendFileMsg msg) throws IOException{
		
		ObjectUtil.writeObject(s, msg);
	}
	
	//��ʾ�ļ���Ϣ
	public void showSendFileMsg(SendFileMsg msg) throws IOException{
		boolean b = DialogUtil.showConfirm(msg.getFrom().getNickname()
				+ ",Ҫ�����ļ�:" + msg.getFilename() + " ���㣬�Ƿ���ܣ�");
		System.out.println("�Ƿ�����ļ���" + b);
		if(b){//�����ļ�
			new ReceiveFileThread(msg, s).start();
		}else{//�������ļ�
			SendFileRs rs = new SendFileRs();
			rs.setFrom(msg.getFrom());
			rs.setTo(msg.getTo());
			rs.setAgreed(false);
			ObjectUtil.writeObject(s, rs);
		}
	}
	
	public void showSendFileRS(SendFileRs rs){
		if(rs.isAgreed()){//ͬ��
			//-->�߳� --> ����Socket --> �����ļ�
			new SendFileThread(rs).start();
		}else{
			DialogUtil.showInfo(rs.getFrom().getNickname() + "�ܾ���������ļ�");
		}
	}
}
