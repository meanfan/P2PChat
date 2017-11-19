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
	UDPMessageListener()
	{
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
			Response response = new Response(Response.TYPE_CHAT_ACCECPT,Response.CHAT_ACCECPT_SUCCESS);
			byte b[]=response.toByte();
			DatagramPacket packet = new DatagramPacket(b,b.length);
			try {
				socket.send(packet);
				ChatThread ct = new ChatThread(false,myName,(String)request.getMyName(),packet.getAddress());
				ct.start();
			} catch (IOException e) {e.printStackTrace();}
		}
		else
		{
			Response response = new Response(Response.TYPE_CHAT_ACCECPT,Response.CHAT_ACCECPT_FAIL);
			DatagramPacket packet = new DatagramPacket(response.toByte(),1024);
			try {
				socket.send(packet);
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	public void run()
	{
		while(true)
		{
			try {
				socket.receive(packet);
				System.out.println("[UDP]Request got");
				request = Request.toRequest(packet.getData());
					if(request.getType()==Request.TYPE_CHAT_REQUEST)
					{
						chat();
					}
				Thread.sleep(500);
				} catch (IOException e) {e.printStackTrace();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
