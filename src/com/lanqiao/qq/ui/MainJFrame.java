package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lanqiao.qq.entity.SendMsg;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.thread.ServerThread;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.ServerSocket;
import java.net.Socket;

public class MainJFrame extends JFrame {

	private JPanel contentPane;
	
	//====================
	private User u;//当前登录用户
	private JPanel panel;
	private Socket s;
	//String --> account 窗口
	private Map<String, ChatJFrame> chats = new HashMap<String, ChatJFrame>();

	/**
	 * Create the frame.
	 */
	public MainJFrame(User u, Socket s) {
		
		//========================
		this.u = u;
		this.s = s;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 240, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\main\\1.PNG"));
		lblNewLabel.setBounds(0, 0, 225, 56);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\main\\2.png"));
		lblNewLabel_1.setBounds(0, 57, 35, 418);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				find(e);
			}
		});
		lblNewLabel_3.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\main\\3.png"));
		lblNewLabel_3.setBounds(0, 475, 225, 52);
		contentPane.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 57, 189, 418);
		contentPane.add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(50, 1, 5, 5));
		
		//===================
		setTitle(u.getAccount());//设置标题为用户账号
		initFriends();
		
		//启动线程监听服务器发送的信息
		new ServerThread(s, u, this).start();
	}
	
	//====================
	
	//初始化好友头像
	private void initFriends(){
		List<User> ulist = u.getFriends();
		for(User f : ulist){
			
			panel.add(initImg(f));
		}
	}
	
	//查找用户
	private void find(MouseEvent e){
		FindJFrame fj = new FindJFrame(s, u);
		fj.setLocationRelativeTo(null);
		fj.setVisible(true);
	}
	
	//添加好友
	public void addFriend(User f){
		
		panel.add(initImg(f));
		this.getContentPane().validate();//重新更新界面
	}
	
	//初始化某个好友的头像
	private JLabel initImg(final User f){
		final JLabel fimg = new JLabel();
		fimg.setIcon(new ImageIcon(getClass()
				.getClassLoader()
				.getResource("img/icon/" + f.getImg() + ".png")));
		fimg.setToolTipText(f.getAccount());
		fimg.setText(f.getNickname());
		//加上点击事件 弹出聊天窗口
		fimg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount() == 2){//判断是否双击鼠标
					fimg.setIcon(new ImageIcon(getClass()
							.getClassLoader()
							.getResource("img/icon/" + f.getImg() + ".png")));
					ChatJFrame cj = chats.get(f.getAccount());
					if(cj == null){
						cj = new ChatJFrame(f, u, s);
						cj.setVisible(true);
						//把新建立的窗口放在map中
						chats.put(f.getAccount(), cj);
					}else{
						cj.setVisible(true);
					}
				}
			}
		});
		return fimg;
	}
	
	//让好友头像跳动
	public void tip(SendMsg msg){
		
		//显示消息到聊天窗口
		ChatJFrame cj = chats.get(msg.getFrom().getAccount());
		if(cj == null){
			cj = new ChatJFrame(msg.getFrom(), u, s);
			cj.setLocationRelativeTo(null);
			chats.put(msg.getFrom().getAccount(), cj);
		}
		cj.appendMsg(msg);//添加到聊天消息
		if(!cj.isVisible()){//如果窗口不可见，则跳动头像
			Component[] imgs = panel.getComponents();
			for(Component c : imgs){
				JLabel jlabel = (JLabel)c;
				if(jlabel.getToolTipText().equals(msg.getFrom().getAccount())){
					jlabel.setIcon(new ImageIcon(getClass()
							.getClassLoader()
							.getResource("img/icon/" + msg.getFrom().getImg() + ".gif")));
					break;
				}
			}
		}
	}
	
	
}
