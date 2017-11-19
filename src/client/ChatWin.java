package client;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ChatWin extends JFrame implements ActionListener{
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
	private InetAddress ip;
	private Request request;
	private DatagramSocket socket;
	private DatagramPacket packet;
	public ChatWin(String myName,String yourName,InetAddress ip)
	{
		this.setTitle("Chating");
        this.setVisible(true);
        this.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
        msg = new JTextArea(4,20);
        msg.setLineWrap(true);
        msg.setWrapStyleWord(true);
        msg.setEditable(false);
        msg.append("等待连接...");
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
		this.myName = myName;
		this.yourName = yourName;
		this.ip = ip;
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == sendButton)
		{
			
		}
		if(b == closeButton)
		{
			
		}
	}
	public static void main(String args[])//测试用
	{
		try {
			new ChatWin("me","you",InetAddress.getLocalHost());
		} catch (UnknownHostException e) {e.printStackTrace();}
	}
}
