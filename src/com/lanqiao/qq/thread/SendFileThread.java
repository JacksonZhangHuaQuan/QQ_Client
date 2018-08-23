package com.lanqiao.qq.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.plaf.ProgressBarUI;

import com.lanqiao.qq.entity.SendFileRs;
import com.lanqiao.qq.ui.ProgressJFrame;
import com.lanqiao.qq.util.DialogUtil;

public class SendFileThread extends Thread{
	
	private SendFileRs rs;
	
	public SendFileThread(SendFileRs rs) {
		this.rs = rs;
	}

	@Override
	public void run() {
		FileInputStream fis = null;
		OutputStream os = null;
		Socket s1 = null;
		try {
			s1 = new Socket(rs.getIp(), rs.getPort());
			System.out.println("�����ļ������ӵ����ܶ�");
			//file -> Socket -->
			File file = new File(rs.getFilepath());
			fis = new FileInputStream(file);
			os = s1.getOutputStream();
			byte bs[] = new byte[1024*5];
			int len = 0;
			ProgressJFrame pj = new ProgressJFrame();
			pj.setFilesize(file.length());
			pj.setLable("�ļ����ڷ�����......");
			pj.setLocationRelativeTo(null);
			pj.setVisible(true);
			while((len=fis.read(bs)) != -1){
				os.write(bs, 0, len);
				pj.setWritesize(len);
			}
			DialogUtil.showInfo("�ļ�������ϣ���");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
				fis.close();
				s1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
