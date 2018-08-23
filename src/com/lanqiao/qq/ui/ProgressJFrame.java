package com.lanqiao.qq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ProgressJFrame extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel label;
	
	//===============================
	private long filesize;//文件大小
	private long writesize;//写了的文件大小
	

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	//100 5+5
	public void setWritesize(long size) {
		writesize = writesize + size;
		long value = 100 * writesize / filesize; 
		progressBar.setValue((int)value);
		progressBar.setString(value + "%");
	}
	
	public void setLable(String msg){
		label.setText(msg);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgressJFrame frame = new ProgressJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProgressJFrame() {
		setTitle("\u6587\u4EF6\u4F20\u9001");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 416, 192);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("");
		label.setBounds(14, 36, 304, 26);
		contentPane.add(label);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(14, 88, 361, 26);
		contentPane.add(progressBar);
	}
}
