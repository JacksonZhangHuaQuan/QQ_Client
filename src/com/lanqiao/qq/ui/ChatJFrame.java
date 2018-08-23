package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lanqiao.qq.biz.UserBiz;
import com.lanqiao.qq.entity.SendFileMsg;
import com.lanqiao.qq.entity.SendMsg;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.DialogUtil;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import javax.swing.JScrollPane;

public class ChatJFrame extends JFrame {

	private JPanel contentPane;

	//=========================
	private User f;//好友
	private User u;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private UserBiz uBiz;

	/**
	 * Create the frame.
	 */
	public ChatJFrame(User f, User u, Socket s) {
		
		//========================
		this.f = f;
		this.u = u;
		uBiz = new UserBiz(s);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 434, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(282, 13, 122, 184);
		lblNewLabel.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\chat\\female.png"));
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setBounds(14, 200, 390, 18);
		label.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\chat\\1.png"));
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(282, 223, 122, 199);
		label_1.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\chat\\male.png"));
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendFile(e);
			}
		});
		label_2.setBounds(24, 390, 33, 32);
		label_2.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\chat\\folder.png"));
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendMsg(e);
			}
		});
		label_3.setBounds(79, 401, 69, 21);
		label_3.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\chat\\send1.png"));
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(183, 401, 72, 21);
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				close(e);
			}
		});
		label_4.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\chat\\close1.png"));
		contentPane.add(label_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 254, 174);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(14, 231, 254, 157);
		contentPane.add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		//===================
		setTitle(f.getNickname());
	}
	
	//=================================
	private void close(MouseEvent e){
		this.setVisible(false);
	}
	
	//发消息
	private void sendMsg(MouseEvent e){
		SendMsg msg = new SendMsg();
		msg.setFrom(u);
		msg.setTo(f);
		msg.setMsg(textArea_1.getText());
		msg.setTime(new Date());
		try {
			uBiz.sedMsg(msg);
			textArea.append(msg.toString());
			textArea_1.setText("");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	//显示消息
	public void appendMsg(SendMsg msg){
		textArea.append(msg.toString());
	}
	
	//发送文件
	public void sendFile(MouseEvent e){
		File file = DialogUtil.openFile();
		SendFileMsg msg = new SendFileMsg();
		msg.setFilename(file.getName());
		msg.setFilesize(file.length());
		msg.setFrom(u);
		msg.setTo(f);
		msg.setFilepath(file.getPath());;
		try {
			uBiz.sendFileMsg(msg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
