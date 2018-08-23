package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lanqiao.qq.biz.SysBiz;
import com.lanqiao.qq.entity.RegeditRS;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.DialogUtil;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegisterJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	//=================
	private JComboBox comboBox;
	private JLabel lblNewLabel_3;
	private LoginFrame loginFrame;
	private SysBiz sBiz;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegisterJFrame frame = new RegisterJFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public RegisterJFrame(LoginFrame loginFrame, Socket s) {
		
		this.loginFrame = loginFrame;
		sBiz = new SysBiz(s);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u6635\u79F0\uFF1A");
		lblNewLabel.setBounds(14, 44, 52, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setBounds(14, 91, 52, 18);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u5E74\u9F84\uFF1A");
		lblNewLabel_2.setBounds(14, 134, 52, 18);
		contentPane.add(lblNewLabel_2);
		
		JLabel label = new JLabel("\u90AE\u7BB1\uFF1A");
		label.setBounds(14, 182, 52, 18);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(66, 41, 157, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(66, 88, 157, 24);
		contentPane.add(passwordField);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				listenerOld(e);
			}
		});
		textField_1.setBounds(66, 131, 157, 24);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(66, 179, 157, 24);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5934\u50CF\uFF1A");
		label_1.setBounds(258, 44, 45, 18);
		contentPane.add(label_1);
		
		//==============================
		ComboBoxModel jComboBox1Model = 
				new DefaultComboBoxModel(
						new String[]{"笑脸","花朵", "笔墨", "脸谱"});
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				changeImg(e);
			}
		});
		comboBox.setModel(jComboBox1Model);
		comboBox.setBounds(314, 41, 72, 24);
		contentPane.add(comboBox);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("src/img/icon/1.png"));
		lblNewLabel_3.setBounds(314, 92, 52, 44);
		contentPane.add(lblNewLabel_3);
		
		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register(e);
			}
		});
		button.setBounds(258, 179, 63, 26);
		contentPane.add(button);
		
		JButton btnNewButton = new JButton("\u5173\u95ED");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close(e);
			}
		});
		btnNewButton.setBounds(344, 178, 63, 27);
		contentPane.add(btnNewButton);
	}
	
	
	//==========================
	// 改变照片
	private void changeImg(ItemEvent e) {
		int index = comboBox.getSelectedIndex();
		lblNewLabel_3.setIcon(new ImageIcon(getClass().getClassLoader().getResource("img/icon/" + (index+1)+ ".png")));
	}
	
	//改变当前页面
	private void close(ActionEvent e){
		this.dispose(); //销毁
		loginFrame.setVisible(true);
	}
	
	//注册
	private void register(ActionEvent e){
		String nickname = textField.getText().trim();
		String password = new String(passwordField.getPassword()).trim();
		String age = textField_1.getText().trim();
		String email = textField_2.getText().trim();
		String img = String.valueOf(comboBox.getSelectedIndex()+1);
		System.out.println(nickname + "=====" + email);
		System.out.println(img);
		User u = new User();
		u.setAge(Integer.parseInt(age));
		u.setNickname(nickname);
		u.setEmail(email);
		u.setPassword(password);
		u.setImg(img);
		try {
			RegeditRS rs = sBiz.register(u);
			if(rs.isRs()){//注册成功
				this.dispose();//销毁当前窗口
				DialogUtil.showInfo(rs.getMsg());
				loginFrame.setVisible(true);
			}else{ //注册失败
				DialogUtil.showAlarm(rs.getMsg());
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	//监听年龄
	public void listenerOld(KeyEvent e){
		textField_1.addKeyListener(new KeyAdapter() {
			 public void keyTyped(KeyEvent event) {
	                char ch = event.getKeyChar();
	                if (ch < '0' || ch > '9') {   //这里填入Unicode码即可
	                    event.consume();
	                }
	            }
		});
	}
	

}
