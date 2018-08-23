package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.lanqiao.qq.biz.SysBiz;
import com.lanqiao.qq.entity.User;
import com.lanqiao.qq.util.DialogUtil;
import com.lanqiao.qq.util.PropertiesUtil;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	
	//===================================
	private JButton button;
	private JButton button_1;
	private Socket s;
	private SysBiz sBiz;
	private JPasswordField passwordField;
	int x;
	int y;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 340, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\qq.PNG"));
		lblNewLabel.setBackground(new Color(175, 238, 238));
		lblNewLabel.setBounds(0, 0, 322, 50);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\r\n");
		lblNewLabel_1.setBounds(14, 13, 72, 18);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\id.png"));
		lblNewLabel_2.setBounds(14, 94, 52, 24);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\pw.png"));
		lblNewLabel_3.setBounds(14, 152, 52, 24);
		contentPane.add(lblNewLabel_3);
		
		textField = new JTextField();
		
		textField.setBounds(80, 94, 139, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\2-2.png"));
		lblNewLabel_4.setBounds(37, 219, 240, 18);
		contentPane.add(lblNewLabel_4);
		
		button = new JButton("");
		button.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\login.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login(e);
			}
		});
		button.setBounds(39, 268, 72, 27);
		contentPane.add(button);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\sz.png"));
		button_1.setBounds(182, 268, 72, 27);
		contentPane.add(button_1);
		
		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				toregister(e);
			}
		});
		label.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\reg.png"));
		label.setBounds(233, 94, 72, 24);
		contentPane.add(label);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				findPassword(e);
			}
		});
		lblNewLabel_5.setIcon(new ImageIcon("D:\\\u5B9E\u8BAD\u9879\u76EE\\QQ_Client\\src\\img\\login\\qh.png"));
		lblNewLabel_5.setBounds(240, 152, 65, 24);
		contentPane.add(lblNewLabel_5);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(80, 152, 139, 24);
		contentPane.add(passwordField);
		
		//====================================
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//获取端口并连接服务端
		try {
			s = new Socket(PropertiesUtil.readPro("ip"), 
					Integer.parseInt(PropertiesUtil.readPro("port")));
			sBiz = new SysBiz(s);
		} catch (IOException e) {
			//弹出提示对话框
			DialogUtil.showAlarm("链接服务器失败！！！");
			e.printStackTrace();
		}
			
	}
	
	//登录
	private void login(ActionEvent e){
		String name = textField.getText().trim();
		String password = new String(passwordField.getPassword()).trim();
		//使用对象序列化
		//1.类必须一致（包名 + 类名）
		//2.实现实例化接口
		User u = new User();
		u.setAccount(name);
		u.setPassword(password);
		try {
			User u1 = sBiz.login(u);
			if(u1 == null){ //失败
				DialogUtil.showAlarm("用户名或者密码错误！");
			}else{//成功
				System.out.println("好友数量" + u1.getFriends().size());
				//DialogUtil.showAlarm("登录成功！");
				this.setVisible(false);
				MainJFrame mj = new MainJFrame(u1, s);
				mj.setLocationRelativeTo(null);
				mj.setVisible(true);
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			DialogUtil.showAlarm("服务器异常!");
		} catch (IOException e1) {
			e1.printStackTrace();
			DialogUtil.showAlarm("服务器异常!");
		}
		
	}

	//去注册页面
	public void toregister(MouseEvent e) {
		this.setVisible(false);//登录窗口消失
		RegisterJFrame rf = new RegisterJFrame(this, s);
		rf.setLocationRelativeTo(null);
		rf.setVisible(true);
	}
	
	//找回密码
	private void findPassword(MouseEvent e) {
		String account = textField.getText().trim();
		FindPassJFrame fpj = new FindPassJFrame(s, account);
		fpj.setLocationRelativeTo(null);
		fpj.setVisible(true);
	}
	
	public void listenAccount(InputMethodEvent e){
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				Document doc = e.getDocument();
				x = doc.getLength();
				if(x < 6){
					DialogUtil.showAlarm("用户名长度太小");
				}else if(x > 12){
					DialogUtil.showAlarm("用户名长度太长");
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				
				
			}
		});
	}
	

}
