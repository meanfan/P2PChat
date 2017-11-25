package client;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import data.Request;
/*
 * 聊天窗口类
 */
public class ChatWin extends JFrame implements ActionListener,Runnable{
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 400;
    public int windowsHeight = 600;
    private JTextArea msg;
    private JTextArea msgSend;
    private JButton sendButton;
    private JButton closeButton;
    private Box baseBoxH,boxV;
	private String myName;
	private String yourName;
	private InetAddress address;
	UDPMessageListener msgListener;//后台UDP消息接收线程
	private Request request;//请求的封装
	private Request requestGot; 
	boolean isClosing = false;//退出程序的通知
	//初始化并创建聊天窗口
	public ChatWin(String myName,String yourName,UDPMessageListener msgListener,InetAddress address)
	{
		this.setTitle("与"+yourName+"聊天中");
        this.setVisible(true);
        this.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
        msg = new JTextArea(4,20);
        msg.setLineWrap(true);
        msg.setWrapStyleWord(true);
        msg.setEditable(false);
        msg.append("");
        msgSend = new JTextArea(3,30);
        msgSend.setLineWrap(true);
        sendButton = new JButton("发送");
        closeButton = new JButton("关闭");
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(new JScrollPane(msg), BorderLayout.CENTER);
        boxV = Box.createVerticalBox();
        boxV.add(Box.createVerticalStrut(10));
        boxV.add(sendButton);
        boxV.add(Box.createVerticalStrut(10));
        boxV.add(closeButton);
        boxV.add(Box.createVerticalStrut(10));
        baseBoxH = Box.createHorizontalBox();
        baseBoxH.add(new JScrollPane(msgSend));
        baseBoxH.add(boxV);
        //messagePanel.add(Box.createVerticalStrut(16));
        messagePanel.add(baseBoxH, BorderLayout.SOUTH);
        add(messagePanel);
        validate();
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        sendButton.addActionListener(this);
        closeButton.addActionListener(this);
		this.myName = myName;
		this.yourName = yourName;
		this.address = address;
		this.msgListener = msgListener;
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton bt = (JButton)e.getSource();
		if(bt == sendButton)//发送按钮事件,发送消息类型request
		{
			String message = msgSend.getText();
			if( message.length() != 0 )
			{
				request = new Request(Request.TYPE_CHAT_MSEEAGE,message);
				byte b[]=request.toByte();
				DatagramPacket packet = new DatagramPacket(b,b.length,address,2333);
				try {
					msgListener.getSocket().send(packet);
					msg.append("\n<" + myName + "(我)>" + message);
					msgSend.setText("");
				} catch (IOException e1) {e1.printStackTrace();}
			}
		}
		if(bt == closeButton)//关闭按钮事件，发送关闭类型request
		{
			request = new Request(Request.TYPE_CHAT_QUIT);
			byte b[]=request.toByte();
			DatagramPacket packet = new DatagramPacket(b,b.length,address,2333);
			try {
				msgListener.getSocket().send(packet);
			} catch (IOException e1) {e1.printStackTrace();}
			isClosing = true;
			this.dispose();
		}
	}
	//通过监听msgListener中的变量isGetMsg来获取收到的回复
	public void run()
	{
		synchronized(this) {
			while(!isClosing)
			{
				while(msgListener.isGetMsg == false)
				{
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
				request = msgListener.getRequest();
				System.out.println("msg got");
				requestGot = new Request(request.getType(),request.getMessage());
				if(msgListener.getRequest().getType() == Request.TYPE_CHAT_QUIT)
				{
					this.dispose();
					break;
				}
				if(msgListener.getAddress()==address)
				{
					msg.append("\n<"+this.yourName+">"+msgListener.getRequest().getMessage());
					msgListener.isGetMsg = false;
				}
			}
		}
	}
}
