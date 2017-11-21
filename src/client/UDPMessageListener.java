package client;

import java.io.IOException;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.*;
/*
 * ��̨UDP��Ϣ����
 */
public class UDPMessageListener extends Thread{
	public int port;//�˿�
	private DatagramPacket packet;//���ݱ�
	public DatagramSocket socket;//���ݱ�socket
	private Request request;//����ķ�װ
	private String myName;
	private String yourName;
	private InetAddress address;
	public int isChatAccecpted;
	private boolean isClosing;
	public boolean isGetMsg;
	public boolean isQuit;
	//��ʼ�����������ݱ�����socket
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
	//ע��ɹ��������û���
	void setMyName(String myName)
	{
		this.myName = myName;
	}
	//��������Ĵ����û�ͬ����ͻظ��������߳�WaitResponseWin
	void chat()
	{
		yourName = request.getName();
		int c = JOptionPane.showConfirmDialog(null, "������������"+yourName+"��\n�Ƿ�ͬ�⣿","��Ϣ",JOptionPane.YES_NO_OPTION);
		
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
	//���socket
	public DatagramSocket getSocket()
	{
		return socket;
	}
	//��õ�ַ
	public InetAddress getAddress()
	{
		return address;
	}
	//�����������
	public Request getRequest()
	{
		return request;
	}
	//�رմ���
	public void close()
	{
		isClosing = true;
		socket.close();
	}
	//���Ͻ������ݱ����󣬸��������а�����������֪ͨ�����߳�
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
					//System.out.println("[UDP]Request��TYPE_CHAT_REQUEST");
					chat();
				}else if(request.getType()==Request.TYPE_CHAT_REQUEST_ACCECPTED)
				{
					//System.out.println("[UDP]Request��TYPE_CHAT_REQUEST_ACCECPTED");
					isChatAccecpted = 1;
				}else if(request.getType()==Request.TYPE_CHAT_REQUEST_REFUSED)
				{
					//System.out.println("[UDP]Request��TYPE_CHAT_REQUEST_REFUSED");
					isChatAccecpted = 2;
				}else if(request.getType()==Request.TYPE_CHAT_MSEEAGE)
				{
					//֪ͨChatWin������Ϣ
					isGetMsg = true;
				}else if(request.getType()==Request.TYPE_CHAT_QUIT)
				{
					//֪ͨChatWin�˳�
					isGetMsg = true;
					isQuit = true;
				}
				Thread.sleep(500);
				} catch (IOException e) {e.printStackTrace();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
