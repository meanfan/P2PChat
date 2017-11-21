package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import data.*;
/*
 * 聊天请求
 */
class ChatRqt extends Thread{
	private String myName;
	private String yourName;
	private InetAddress address;//对方地址
	private Request request;//请求的封装
	UDPMessageListener msgListener;//后台UDP消息接收线程
	DatagramSocket socket;//已建立的socket
	DatagramPacket chatRequest;//请求的数据包
	DatagramPacket chatRqtResponse;//回复的数据包
	//传入各参数
	ChatRqt(UDPMessageListener msgListener,String myName,String yourName,InetAddress address)
	{
		this.msgListener = msgListener;
		this.socket = msgListener.getSocket();
		this.myName = myName;
		this.yourName = yourName;
		this.address = address;
	}
	//发送聊天请求，成功发送后启动WaitResponseWin线程，本线程结束
	public void run() {
		
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
