package client;

import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import data.Request;
import data.Response;
/*
 *ע������� 
 */
public class Register extends JPanel implements ActionListener{

	private Box baseBox,subBoxH1,subBoxH2,boxV1,boxV2;
	private JTextField tfIP,tfName;
	private JButton btn;
	public InetAddress address;
	public String name;
	private TCPCommWithServer message;
	private Request request;
	private Response response;
	boolean isRegister = false;
	public Register()
	{
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("���������IP��"));
		boxV1.add(Box.createVerticalStrut(16));
		boxV1.add(new JLabel("�����û�����"));
		boxV2 = Box.createVerticalBox();
		tfIP = new JTextField(16);
		boxV2.add(tfIP);
		boxV2.add(Box.createVerticalStrut(16));
		tfName = new JTextField(16);
		boxV2.add(tfName);
		subBoxH1 = Box.createHorizontalBox();
		subBoxH1.add(boxV1);
		subBoxH1.add(Box.createHorizontalStrut(20));
		subBoxH1.add(boxV2);
		subBoxH2 = Box.createVerticalBox();
		btn = new JButton("ȷ��");
		btn.addActionListener(this);
		subBoxH2.add(btn);
		baseBox = Box.createVerticalBox();
		baseBox.add(subBoxH1);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(subBoxH2);
		add(baseBox);
		validate();
		tfIP.setText("127.0.0.1");
		tfName.setText("test");
	}
	public String getName()
	{
		return name;
	}
	public InetAddress getAddress()
	{
		return address;
	}
	public boolean isRegistered()
	{
		return isRegister;
	}
	public void actionPerformed(ActionEvent e)
	{
		if(isRegister==true)
		{
			JOptionPane.showMessageDialog(this, "�����ظ�ע�ᡣ");
			return;
		}
		if(tfIP.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(null, "�����������IP");
			return;
		}
		if(tfName.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(null, "�������û���");
			return;
		}
		try {
			address = InetAddress.getByName(tfIP.getText());
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(null, "������IP����");
			e1.printStackTrace();
			}
		name = tfName.getText();
		request = new Request(Request.TYPE_REGISTER,name);
		message = new TCPCommWithServer(address,request);
		response=message.getResponse();
		if(response!=null && response.getType()==Request.TYPE_REGISTER)
		{
			if(response.getSuccess()==true)
			{
				JOptionPane.showMessageDialog(null, "ע��ɹ���");
				new UDPMessageListener();
				isRegister = true;
				
			}
			else
				JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�IP/�û����Ѵ���");
		}
		else
			JOptionPane.showMessageDialog(null, "�������ͨѶ����");
	}
}
