package com.lanqiao.qq.util;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DialogUtil {
	
	public static void showAlarm(String str){
		JOptionPane.showMessageDialog(null, str, "ϵͳ����", JOptionPane.WARNING_MESSAGE);
	}

	public static void showInfo(String str){
		JOptionPane.showMessageDialog(null, str, "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean showConfirm(String str){
		int i =JOptionPane.showConfirmDialog(null, str, "ϵͳȷ��", JOptionPane.YES_NO_CANCEL_OPTION);
		if(i == JOptionPane.OK_OPTION){
			return true;
		}else{
			return false;
		}
	}
	
	//���ļ�
	public static File openFile(){
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(null);
		return jfc.getSelectedFile();
	}
	
	//�����ļ�
	public static File saveFile(String filename){
		JFileChooser jfc = new JFileChooser();
		jfc.setSelectedFile(new File(filename));
		jfc.showSaveDialog(null);
		return jfc.getSelectedFile();
	}
	
}
