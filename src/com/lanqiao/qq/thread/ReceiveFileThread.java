package com.lanqiao.qq.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.lanqiao.qq.entity.SendFileMsg;
import com.lanqiao.qq.entity.SendFileRs;
import com.lanqiao.qq.ui.ProgressJFrame;
import com.lanqiao.qq.util.DialogUtil;
import com.lanqiao.qq.util.ObjectUtil;

public class ReceiveFileThread extends Thread{
	
	private SendFileMsg msg;
	private Socket s;
	
	public ReceiveFileThread(SendFileMsg msg, Socket s) {
		this.msg = msg;
		this.s = s;
	}
	
	@Override
	public void run() {
		File f = DialogUtil.saveFile(msg.getFilename());
		SendFileRs rs = new SendFileRs();
		rs.setFrom(msg.getTo());
		rs.setTo(msg.getFrom());
		rs.setAgreed(true);
		rs.setFilepath(msg.getFilepath());//�����ļ�·��
		ServerSocket ss = null;
		Socket s1 = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			ss = new ServerSocket(0);// 0 -> ϵͳ����һ���˿ں�
			ss.setSoTimeout(1000*60*2);
			rs.setPort(ss.getLocalPort());
			rs.setIp(InetAddress.getLocalHost().getHostAddress().toString());//��ȡ������IP��ַ
			System.out.println(ss.getLocalPort() + "========" + InetAddress.getLocalHost().getHostAddress().toString());
			ObjectUtil.writeObject(s, rs);//�Ѵ����Ľ��ܶ���Ϣ�������Ͷ�
			s1 = ss.accept();
			System.out.println("�ļ��������ӳɹ�������");
			is = s1.getInputStream();
			fos = new FileOutputStream(f);
			byte bs[] = new byte[1024*5];
			int len = 0;
			ProgressJFrame pj = new ProgressJFrame();
			pj.setFilesize(msg.getFilesize());
			pj.setLable("�ļ�������......");
			pj.setLocationRelativeTo(null);
			pj.setVisible(true);
			while((len=is.read(bs)) > 0){
				fos.write(bs, 0, len);
				pj.setWritesize(len);
			}
			System.out.println("�ļ����ܳɹ�");
			DialogUtil.showInfo("�ļ��������");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				fos.close();
				is.close();
				s1.close();
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
