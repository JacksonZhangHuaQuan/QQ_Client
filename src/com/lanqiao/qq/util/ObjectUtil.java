package com.lanqiao.qq.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectUtil {

	public static void writeObject(Socket s, Object o) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(o);
	}
	
	public static Object readObject(Socket s) throws IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		return ois.readObject();
	}
}
