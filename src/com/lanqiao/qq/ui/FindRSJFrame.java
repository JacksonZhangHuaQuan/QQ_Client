package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.lanqiao.qq.biz.UserBiz;
import com.lanqiao.qq.entity.AddFriendsMsg;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.DialogUtil;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class FindRSJFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	//=================================
	private List<User> ulist;
	private User u;//当前登录用户
	private UserBiz uBiz;
//	private Socket s;
	
	/**
	 * Create the frame.
	 */
	public FindRSJFrame(List<User> ulist, User u, Socket s) {
		
		//===================
		this.ulist = ulist;
		this.u = u;
//		this.s = s;
		this.uBiz = new UserBiz(s);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 496, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 449, 172);
		contentPane.add(scrollPane);
		TableModel jTableModel = 
				new DefaultTableModel(
						getForTable(),
						new String[]{"账号","昵称"});
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(jTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton button = new JButton("\u67E5\u770B\u4FE1\u606F");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInfo(e);
			}
		});
		button.setBounds(64, 198, 113, 27);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u6DFB\u52A0\u597D\u53CB");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFriends(e);
			}
		});
		button_1.setBounds(244, 198, 113, 27);
		contentPane.add(button_1);
	}
	
	//==================================
	//把list中的User信息转换为String[][]格式
	private String[][] getForTable(){
		String strs[][] = new String[ulist.size()][2];
		for(int x = 0; x < ulist.size(); x++){
			strs[x][0] = ulist.get(x).getAccount();
			strs[x][1] = ulist.get(x).getNickname();
		}
		return strs;
	}
	
	private void showInfo(ActionEvent e){
		int index = table.getSelectedRow();
		if(index != -1){//选中
			InfoJFrame info = new InfoJFrame(ulist.get(index));
			info.setLocationRelativeTo(null);
			info.setVisible(true);
		}else{//没有选中
			DialogUtil.showAlarm("请选择一个用户！！！");
		}
	}
	
	private void addFriends(ActionEvent e){
		int index = table.getSelectedRow();
		if(index != -1){
			User f = ulist.get(index);
			AddFriendsMsg msg = new AddFriendsMsg();
			msg.setTo(f);
			msg.setFrom(u);
			try {
				uBiz.addFriends(msg);//发送添加好友请求！！
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else{
			DialogUtil.showAlarm("请选择一个用户！！！");
		}
	}
	
}
