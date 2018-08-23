package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lanqiao.qq.biz.SysBiz;
import com.lanqiao.qq.entity.Find;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.DialogUtil;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.JDBCType;
import java.util.List;
import java.awt.event.ActionEvent;

public class FindJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	//=================================
	private SysBiz sBiz;
	private ButtonGroup bg;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private Socket s;
	private User u;

	/**
	 * Create the frame.
	 */
	public FindJFrame(Socket s, User u) {
		
		//=======================
		this.s  = s;
		this.u = u;
		sBiz = new SysBiz(s);
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 367, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rdbtnNewRadioButton = new JRadioButton("\u7CBE\u786E\u67E5\u627E");
		rdbtnNewRadioButton.setBounds(10, 21, 157, 27);
		contentPane.add(rdbtnNewRadioButton);
		
		textField = new JTextField();
		textField.setBounds(35, 70, 275, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		rdbtnNewRadioButton_1 = new JRadioButton("\u67E5\u627E\u6240\u6709");
		rdbtnNewRadioButton_1.setBounds(10, 126, 157, 27);
		rdbtnNewRadioButton_1.setSelected(true);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JButton btnNewButton = new JButton("\u67E5\u627E");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				find(e);
			}
		});
		btnNewButton.setBounds(35, 199, 102, 27);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("\u5173\u95ED");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close(e);
			}
		});
		button.setBounds(222, 199, 88, 27);
		contentPane.add(button);
		
		bg = new ButtonGroup();
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
	}
	
	//关闭
	private void close(ActionEvent e){
		this.dispose();
	}
	
	// 查找
	private void find(ActionEvent e){
		//精确查找 查找所有
		Find find = new Find();
		find.setUaccount(u.getAccount());
		if(rdbtnNewRadioButton.isSelected()){
			//精确查找
			find.setType(Find.ONE);
			find.setFaccount(textField.getText().trim());//设置账号
		} else{ //查找所有
			find.setType(Find.All);
		}
		//把信息发送给服务器
		try {
			sBiz.find(find);
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
