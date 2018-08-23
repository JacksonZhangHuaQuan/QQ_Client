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
	 * ��֤�û��� �����Ƿ���ȷ
	 * param u
	 * return ����null�����¼ʧ�ܣ��������User��������Ϣ��
	 */
	public User login(User u) throws IOException, ClassNotFoundException {
		
		//1.��userд����ȥ -�� �����
		ObjectUtil.writeObject(s, u);
		
		//2.��ȡ����˵���֤����
		return (User) ObjectUtil.readObject(s);
		
	}
	
	
	// ע�� �ɹ������˻� ���󷵻���ʾ!!!
	public RegeditRS register(User u) throws IOException, ClassNotFoundException {
		// 1.��userд����ȥ -�� �����
		ObjectUtil.writeObject(s, u);

		// 2.��ȡ����˵���֤����
		return (RegeditRS) ObjectUtil.readObject(s);
	}
	
	//���Ͳ��Һ���
	public void find(Find find) throws IOException, ClassNotFoundException {
		
		ObjectUtil.writeObject(s, find);
	}
	
	//��ʾ���ҽ��
	public void showFindRS(List<User> ulist, User u){
		
		System.out.println("��ѯ���������" + ulist.size());
		if(ulist.size() == 0){//û�в�ѯ���
			DialogUtil.showAlarm("û�в�ѯ���κν��!!!");
		}else{
			FindRSJFrame rs = new FindRSJFrame(ulist, u, s);
			rs.setLocationRelativeTo(null);
			rs.setVisible(true);
		}
	}
	
	//��ʾ��Ӻ�����Ϣ
	public void showAddMsg(AddFriendsMsg msg, MainJFrame mj) throws IOException{
		
		//��ȷ�϶Ի���
		boolean b = DialogUtil.showConfirm(msg.getFrom().getNickname() + "���������Ϊ���ѣ��Ƿ�ͬ�⣿");
		
		if(b){//ͬ�� -> ���ͷ��������
			System.out.println("ͬ�⣡����");
			mj.addFriend(msg.getFrom());
		}else{// ��ͬ��
			System.out.println("��ͬ�⣡��");
		}
		
		AddRSMsg rs = new AddRSMsg();
		rs.setForm(msg.getTo());
		rs.setTo(msg.getFrom());
		rs.setAgree(b);
		ObjectUtil.writeObject(s, rs);
	}
	
	
	//��Ӻ��ѵĽ��
	public void showAddRS(AddRSMsg rs, MainJFrame mj){
		if(rs.isAgree()){
			//ͬ��
			DialogUtil.showInfo(rs.getForm().getNickname() + "ͬ��������Ӻ������󣡣�");
			mj.addFriend(rs.getForm());
		}else{
			DialogUtil.showAlarm(rs.getForm().getNickname() + "�ܾ�����ĺ������󣡣�");
		}
	}
	
	//�һ�����
	public User findPass(User u) throws IOException, ClassNotFoundException {
		// 1.��userд����ȥ -�� �����
		ObjectUtil.writeObject(s, u);

		// 2.��ȡ����˵���֤����
		return (User) ObjectUtil.readObject(s);
	}
}
