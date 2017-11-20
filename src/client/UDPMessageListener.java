package client;

import java.io.IOException;
import java.net.*;

import javax.swing.JOptionPane;

import data.*;

public class UDPMessageListener extends Thread{
	DatagramPacket packet;
	DatagramSocket socket;
	Request request;
	String myName;
	String yourName;
	InetAddress address;
	public int isChatAccecpted;
	UDPMessageListener()
	{
		isChatAccecpted = 0;
		byte b2[] = new byte[1024];
		packet = new DatagramPacket(b2,b2.length);
		try {
			socket = new DatagramSocket(2333);
		} catch (SocketException e) {e.printStackTrace();}
	}
	void chat()
	{
		int c = JOptionPane.showConfirmDialog(null, "聊天请求来自"+request.getMyName()+"。\n是否同意？","信息",JOptionPane.YES_NO_OPTION);
		
		if(c==JOptionPane.YES_OPTION)
		{
			Request request = new Request(Request.TYPE_CHAT_REQUEST_ACCECPTED);
			byte b[]=request.toByte();
			DatagramPacket packet = new DatagramPacket(b,b.length,address,2333);
			try {
				System.out.println("also request accecpted sending");
				socket.send(packet);
				System.out.println("also request accecpted sent");
				new ChatWin(myName,yourName,this);
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
	public void run()
	{
		while(true)
		{
			try {
				socket.receive(packet);
				System.out.println("[UDP]Request got");
				address = packet.getAddress();
				request = Request.toRequest(packet.getData());
				if(request.getType()==Request.TYPE_CHAT_REQUEST)
				{
					System.out.println("[UDP]Request：TYPE_CHAT_REQUEST");
					chat();
				}else if(request.getType()==Request.TYPE_CHAT_REQUEST_ACCECPTED)
				{
					System.out.println("[UDP]Request：TYPE_CHAT_REQUEST_ACCECPTED");
					isChatAccecpted = 1;
				}else if(request.getType()==Request.TYPE_CHAT_REQUEST_REFUSED)
				{
					System.out.println("[UDP]Request：TYPE_CHAT_REQUEST_REFUSED");
					isChatAccecpted = 2;
				}else
				{
					
				}
				Thread.sleep(500);
				} catch (IOException e) {e.printStackTrace();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
