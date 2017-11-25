package client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

import javax.swing.*;

import data.Request;
import data.Response;

/*
 * 聊天列表类
 */
public class ChatTable extends JPanel implements ActionListener{
	private JButton btn1,btn2;
	private Box box1,box2, basebox;
	private JTable tb;
	private String myName;
	private InetAddress serverAddress;
	private Register register;
	private Request request;
	private Response response;
	private TCPCommWithServer message;
	private HashMap<String,InetAddress> table;
	private Object nameList[][];
	private Object title[] = {"用户名"};
	ChatTable(Register register){
		btn1 = new JButton("获取列表");
		btn1.addActionListener(this);
		btn2 = new JButton("开始聊天");
		btn2.addActionListener(this);
		box1 = Box.createHorizontalBox();
		box1.add(btn1);
		box1.add(Box.createHorizontalStrut(20));
		box1.add(btn2);
		box2 = Box.createVerticalBox();
		basebox = Box.createVerticalBox();
		basebox.add(box1);
		basebox.add(box2);
		add(basebox);
		validate();
		this.register = register;
		
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(register.isRegistered() == false)
		{
			JOptionPane.showMessageDialog(null, "请先注册！");
			return;
		}
		if(b == btn1)
		{
			myName = register.getName();
			serverAddress = register.getAddress();
			request = new Request(Request.TYPE_USER_LIST);
			message = new TCPCommWithServer(serverAddress,request);
			response=message.getResponse();
			if(response!=null && response.getType()==Response.TYPE_USERS_LIST)
			{
				table = response.getTable();
				//System.out.println(table.get("test"));
				int size = table.size();
				nameList = new Object[size][1];
				String s[] = new String[size];
				table.keySet().toArray(s);
				for(int i=0;i<size;i++)
					nameList[i][0] = s[i];
				tb = new JTable(nameList,title);
				//tb.setEnabled(false);
				box2.removeAll();
				box2.add(tb);
				validate();
			}
			else
				JOptionPane.showMessageDialog(null, "与服务器通讯错误");
			
		}
		if(b == btn2)
		{
			int row = tb.getSelectedRow();
			if(row!=-1) 
			{
				String name2Chat = (String)nameList[row][0];
				if(name2Chat.matches(myName))
				{
					JOptionPane.showMessageDialog(null, "不能和自己聊天！");
					return;
				}
				InetAddress address2Chat = table.get(name2Chat);
				System.out.println("Ready to chat:"+name2Chat+"|"+address2Chat);
				ChatRqt ct = new ChatRqt(register.msgListener,myName,name2Chat,address2Chat);
				ct.start();
			}
			else
				JOptionPane.showMessageDialog(null, "请选择一个聊天对象！");
		}
	}
}
