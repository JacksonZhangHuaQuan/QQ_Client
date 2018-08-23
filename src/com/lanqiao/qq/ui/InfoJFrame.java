package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lanqiao.qq.entity.User;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoJFrame extends JFrame {

	private JPanel contentPane;
	//==========================
	private User u;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_9;

	/**
	 * Create the frame.
	 */
	public InfoJFrame(User u) {
		
		//========================
		this.u = u;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u53F7\uFF1A");
		label.setBounds(14, 43, 57, 25);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u6635\u79F0\uFF1A");
		label_1.setBounds(14, 93, 57, 25);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u5E74\u9F84\uFF1A");
		label_2.setBounds(14, 135, 57, 25);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u90AE\u7BB1\uFF1A");
		label_3.setBounds(14, 177, 57, 25);
		contentPane.add(label_3);
		
		label_4 = new JLabel("");
		label_4.setBounds(71, 46, 154, 22);
		contentPane.add(label_4);
		
		label_5 = new JLabel("");
		label_5.setBounds(71, 96, 154, 22);
		contentPane.add(label_5);
		
		label_6 = new JLabel("");
		label_6.setBounds(71, 138, 154, 22);
		contentPane.add(label_6);
		
		label_7 = new JLabel("");
		label_7.setBounds(71, 180, 154, 22);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("\u5934\u50CF\uFF1A");
		label_8.setBounds(273, 43, 51, 25);
		contentPane.add(label_8);
		
		label_9 = new JLabel("");
		label_9.setBounds(315, 81, 51, 50);
		contentPane.add(label_9);
		
		JButton button = new JButton("\u5173\u95ED");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close(e);
			}
		});
		button.setBounds(282, 176, 84, 27);
		contentPane.add(button);
		
		//=======================
		initInfo();
	}
	
	//================================
	//关闭
	private void close(ActionEvent e){
		this.dispose();
	}
	
	//获取用户信息
	private void initInfo(){
		label_4.setText(u.getAccount());
		label_5.setText(u.getNickname());
		label_6.setText(String.valueOf(u.getAge()));
		label_7.setText(u.getEmail());
		System.out.println("picture :" + u.getImg());
		label_9.setIcon(new ImageIcon(getClass()
				.getClassLoader()
				.getResource("img/icon/" + u.getImg() + ".png")));
	}
	
}
