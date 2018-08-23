package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lanqiao.qq.biz.SysBiz;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.DialogUtil;

import javax.swing.JLabel;
import java.awt.Font;
import java.net.Socket;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class FindPassJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	//==================================
	private SysBiz sBiz;
	private String account;

	/**
	 * Create the frame.
	 */
	public FindPassJFrame(Socket s, String account) {
		
		//============================
		sBiz = new SysBiz(s);
		this.account = account;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 330, 296);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u53F7\uFF1A");
		label.setFont(new Font("隶书", Font.BOLD, 20));
		label.setBounds(14, 34, 79, 32);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u90AE\u7BB1\uFF1A");
		label_1.setFont(new Font("隶书", Font.BOLD, 20));
		label_1.setBounds(14, 106, 79, 32);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(94, 37, 200, 30);
		textField.setText(account);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(94, 108, 200, 30);
		contentPane.add(textField_1);
		
		JButton button = new JButton("\u63D0\u4EA4");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findPass(e);
			}
		});
		button.setFont(new Font("隶书", Font.BOLD, 20));
		button.setBounds(35, 183, 98, 32);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close(e);
			}
		});
		button_1.setFont(new Font("隶书", Font.BOLD, 20));
		button_1.setBounds(173, 183, 98, 32);
		contentPane.add(button_1);
	}
	
	//找回密码
	private void findPass(ActionEvent e){
		String email = textField_1.getText().trim();
		String acc = textField.getText().trim();
		User u = new User();
		u.setAccount(acc);
		u.setEmail(email);
		try {
			User u1 = sBiz.findPass(u);
			if(u1 != null){
				DialogUtil.showInfo("你好" +u1.getAccount() + " 你的密码是：" + u1.getPassword());
				this.dispose();
			}else{
				DialogUtil.showAlarm("账号不存在或者邮箱错误");
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	//关闭
	private void close(ActionEvent e){
		this.dispose();
	}
}
