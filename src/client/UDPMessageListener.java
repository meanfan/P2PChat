package client;

import java.io.IOException;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.*;

public class UDPMessageListener extends Thread{
	public int port;
	private DatagramPacket packet;
	public DatagramSocket socket;
	private Request request;
	private String myName;
	private String yourName;
	private InetAddress address;
	public int isChatAccecpted;
	private boolean isClosing;
	public boolean isGetMsg;
	public boolean isQuit;
	UDPMessageListener(int port)
	{
		this.port = port;
		isChatAccecpted = 0;
		isClosing = false;
		isQuit = false;
		byte b2[] = new byte[1024];
		packet = new DatagramPacket(b2,b2.length);
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {e.printStackTrace();}
	}
	void setMyName(String myName)
	{
		this.myName = myName;
	}
	void chat()
	{
		yourName = request.getName();
		int c = JOptionPane.showConfirmDialog(null, "聊天请求来自"+yourName+"。\n是否同意？","信息",JOptionPane.YES_NO_OPTION);
		
		if(c==JOptionPane.YES_OPTION)
		{
			Request request = new Request(Request.TYPE_CHAT_REQUEST_ACCECPTED);
			byte b[]=request.toByte();
			DatagramPacket packet = new DatagramPacket(b,b.length,address,2333);
			try {
				System.out.println("also request accecpted sending");
				socket.send(packet);
				System.out.println("also request accecpted sent");
				WaitResponseWin waitWin = new WaitResponseWin(this,false);
				waitWin.setChatInfo(myName, yourName);
				Thread waitWinThread = new Thread(waitWin);
				waitWinThread.start();
			} catch (IOException e) {e.printStackTrace();}
		}
		else
		{
			Request request = new Request(Request.TYPE_CHAT_REQUEST_REFUSED);
			byte b[]=request.toByte();
			DatagramPacket packet = new DatagramPacket(b,b.length,address,2333);
			try {
				System.out.println("also request refused sending");
				socket.send(packet);
				System.out.println("also request refused sent");
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	public DatagramSocket getSocket()
	{
		return socket;
	}
	public InetAddress getAddress()
	{
		return address;
	}
	public Request getRequest()
	{
		return request;
	}
	
	public void close()
	{
		isClosing = true;
		socket.close();
	}
	public void run()
	{
		
		while(!isClosing)
		{
			try {
				socket.receive(packet);
				System.out.println("[UDP]Request got");
				address = packet.getAddress();
				request = Request.toRequest(packet.getData());
				if(request.getType()==Request.TYPE_CHAT_REQUEST)
				{
					//System.out.println("[UDP]Request：TYPE_CHAT_REQUEST");
					chat();
				}else if(request.getType()==Request.TYPE_CHAT_REQUEST_ACCECPTED)
				{
					//System.out.println("[UDP]Request：TYPE_CHAT_REQUEST_ACCECPTED");
					isChatAccecpted = 1;
				}else if(request.getType()==Request.TYPE_CHAT_REQUEST_REFUSED)
				{
					//System.out.println("[UDP]Request：TYPE_CHAT_REQUEST_REFUSED");
					isChatAccecpted = 2;
				}else if(request.getType()==Request.TYPE_CHAT_MSEEAGE)
				{
					//通知ChatWin有新消息
					isGetMsg = true;
				}else if(request.getType()==Request.TYPE_CHAT_QUIT)
				{
					//通知ChatWin退出
					isGetMsg = true;
					isQuit = true;
				}
				Thread.sleep(500);
				} catch (IOException e) {e.printStackTrace();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
