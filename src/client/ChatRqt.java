package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import data.*;
/*
 * 聊天线程类，主要是聊天请求的建立
 */
class ChatRqt extends Thread{
	private String myName;
	private String yourName;
	private InetAddress address;
	private Request request;
	private UDPMessageListener msgListener;
	private DatagramSocket socket;
	DatagramPacket chatRequest;
	DatagramPacket chatRqtResponse;
	ChatRqt(UDPMessageListener msgListener,String myName,String yourName,InetAddress address)
	{
		this.msgListener = msgListener;
		this.socket = msgListener.getSocket();
		this.myName = myName;
		this.yourName = yourName;
		this.address = address;
	}
	public void run() {
		//聊天请求
		request = new Request(Request.TYPE_CHAT_REQUEST,myName);
		System.out.println("my name:"+myName);
		byte b1[] = request.toByte();
		chatRequest = new DatagramPacket(b1,b1.length,address,2333);
		try {
			//socket = new DatagramSocket(2333,address);
			socket.send(chatRequest);
			System.out.println("chat request sent to" +address);
			byte b2[] = new byte[1024];
			chatRqtResponse = new DatagramPacket(b2,b2.length);
			WaitResponseWin waitWin = new WaitResponseWin(msgListener,true);
			waitWin.setChatInfo(myName, yourName);
			waitWin.setVisible(true);
			Thread waitWinThread = new Thread(waitWin);
			waitWinThread.start();
			//new ChatWin(myName,yourName,address);
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null, "与用户"+yourName+"请求连接失败");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "与用户"+yourName+"请求连接失败");
			e.printStackTrace();
		}
	}
}
