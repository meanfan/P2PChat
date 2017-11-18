package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.HashMap;

import javax.swing.*;

import data.Request;
import data.Response;


public class ChatTable extends JPanel implements ActionListener{
	private JButton btn1,btn2;
	private Box box1,box2, basebox;
	private JTable tb;
	private String myName;
	private String ip;
	private Request request;
	private Response response;
	private Message message;
	private HashMap<String,InetAddress> table;
	Object nameList[][];
	Object title[] = {"用户名"};
	ChatTable(String name){
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
		myName = name;
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == btn1)
		{
			request = new Request(2);
			message = new Message(ip,request);
			response=message.getResponse();
			if(response!=null && response.getType()==2)
			{
				
				table = response.getTable();
				System.out.println(table.get("test"));
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
			Object s = nameList[tb.getSelectedRow()][0];
			InetAddress a = table.get(s);
			new ChatWin(myName,(String)s,a);
		}
	}
	
}
