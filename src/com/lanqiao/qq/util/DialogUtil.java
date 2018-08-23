package com.lanqiao.qq.util;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DialogUtil {
	
	public static void showAlarm(String str){
		JOptionPane.showMessageDialog(null, str, "系统警告", JOptionPane.WARNING_MESSAGE);
	}

	public static void showInfo(String str){
		JOptionPane.showMessageDialog(null, str, "系统消息", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean showConfirm(String str){
		int i =JOptionPane.showConfirmDialog(null, str, "系统确认", JOptionPane.YES_NO_CANCEL_OPTION);
		if(i == JOptionPane.OK_OPTION){
			return true;
		}else{
			return false;
		}
	}
	
	//打开文件
	public static File openFile(){
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(null);
		return jfc.getSelectedFile();
	}
	
	//保存文件
	public static File saveFile(String filename){
		JFileChooser jfc = new JFileChooser();
		jfc.setSelectedFile(new File(filename));
		jfc.showSaveDialog(null);
		return jfc.getSelectedFile();
	}
	
}
