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
	private User u;//��ǰ��¼�û�
	private JPanel panel;
	private Socket s;
	//String --> account ����
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
		setTitle(u.getAccount());//���ñ���Ϊ�û��˺�
		initFriends();
		
		//�����̼߳������������͵���Ϣ
		new ServerThread(s, u, this).start();
	}
	
	//====================
	
	//��ʼ������ͷ��
	private void initFriends(){
		List<User> ulist = u.getFriends();
		for(User f : ulist){
			
			panel.add(initImg(f));
		}
	}
	
	//�����û�
	private void find(MouseEvent e){
		FindJFrame fj = new FindJFrame(s, u);
		fj.setLocationRelativeTo(null);
		fj.setVisible(true);
	}
	
	//��Ӻ���
	public void addFriend(User f){
		
		panel.add(initImg(f));
		this.getContentPane().validate();//���¸��½���
	}
	
	//��ʼ��ĳ�����ѵ�ͷ��
	private JLabel initImg(final User f){
		final JLabel fimg = new JLabel();
		fimg.setIcon(new ImageIcon(getClass()
				.getClassLoader()
				.getResource("img/icon/" + f.getImg() + ".png")));
		fimg.setToolTipText(f.getAccount());
		fimg.setText(f.getNickname());
		//���ϵ���¼� �������촰��
		fimg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount() == 2){//�ж��Ƿ�˫�����
					fimg.setIcon(new ImageIcon(getClass()
							.getClassLoader()
							.getResource("img/icon/" + f.getImg() + ".png")));
					ChatJFrame cj = chats.get(f.getAccount());
					if(cj == null){
						cj = new ChatJFrame(f, u, s);
						cj.setVisible(true);
						//���½����Ĵ��ڷ���map��
						chats.put(f.getAccount(), cj);
					}else{
						cj.setVisible(true);
					}
				}
			}
		});
		return fimg;
	}
	
	//�ú���ͷ������
	public void tip(SendMsg msg){
		
		//��ʾ��Ϣ�����촰��
		ChatJFrame cj = chats.get(msg.getFrom().getAccount());
		if(cj == null){
			cj = new ChatJFrame(msg.getFrom(), u, s);
			cj.setLocationRelativeTo(null);
			chats.put(msg.getFrom().getAccount(), cj);
		}
		cj.appendMsg(msg);//��ӵ�������Ϣ
		if(!cj.isVisible()){//������ڲ��ɼ���������ͷ��
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
