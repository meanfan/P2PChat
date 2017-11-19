package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import data.*;
/*
 * �����߳��࣬��Ҫ����������Ľ���
 */
class ChatThread extends Thread{
	private boolean need2Accecpt;
	private String myName;
	private String yourName;
	private InetAddress address;
	private Request request;
	private DatagramSocket socket;
	ChatThread(boolean need2Accecpt,String myName,String yourName,InetAddress address)
	{
		this.need2Accecpt = need2Accecpt;
		this.myName = myName;
		this.yourName = yourName;
		this.address = address;
	}
	public void run() {
		//��������
		if(need2Accecpt == true)
		{
			request = new Request(Request.TYPE_CHAT_REQUEST,myName);
			byte b1[] = request.toByte();
			DatagramPacket chatRequest = new DatagramPacket(b1,b1.length,address,2333);
			try {
				socket = new DatagramSocket(2333,address);
				socket.send(chatRequest);
				byte b2[] = new byte[1024];
				DatagramPacket chatResquestResponsed = new DatagramPacket(b2,b2.length);
				LinkDialog dialog = new LinkDialog(null,socket,chatResquestResponsed);
				dialog.setChatInfo(myName, yourName, address);
				dialog.setVisible(true);
				Thread chatRqt = new Thread(dialog);
				chatRqt.start();
				new ChatWin(myName,yourName,address);
			} catch (SocketException e) {
				JOptionPane.showMessageDialog(null, "���û�"+yourName+"��������ʧ��");
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "���û�"+yourName+"��������ʧ��");
				e.printStackTrace();
			}
			socket.close();
		}
		else
		{
			new ChatWin(myName,yourName,address);
		}
	}
}
class LinkDialog extends JDialog implements ActionListener,Runnable{
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 300;
    public int windowsHeight = 100;
    private DatagramSocket socket;
    private DatagramPacket packet;
	private JButton cancel;
	int message=0;
	private String myName;
	private String yourName;
	private InetAddress address;
	public void setChatInfo(String myName,String yourName,InetAddress address)
	{
		this.myName = myName;
		this.yourName = yourName;
		this.address = address;
	}
	LinkDialog(JFrame f,DatagramSocket socket,DatagramPacket packet)
	{
		super(f,"��Ϣ",false);
		this.socket = socket;
		this.packet = packet;
		setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		JLabel label = new JLabel("�ȴ��Է�ͬ��...");
		add(label,BorderLayout.CENTER);
		cancel = new JButton("ȡ��");
		cancel.addActionListener(this);
		add(cancel,BorderLayout.SOUTH);
		validate();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancel)
		{
			message = 1;
			setVisible(false);
			Thread.interrupted();
		}
	}
	public int getMessage()
	{
		return message;
	}
	public void run() {
		try {
				socket.receive(packet);
				System.out.println("Responsed got");
				Response response= Response.toResponse(packet.getData());
				setVisible(false);
				if(response.getType()==Response.TYPE_CHAT_ACCECPT)
				{
					if(response.getSuccess() ==Response.CHAT_ACCECPT_SUCCESS)
						new ChatWin(myName,yourName,address);
					else
						JOptionPane.showMessageDialog(null, "�û�"+yourName+"�ܾ��������������");
				}
				socket.close();
			}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
