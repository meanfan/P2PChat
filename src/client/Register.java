package client;

import java.awt.event.*;
import javax.swing.*;

import data.Request;
import data.Response;

public class Register extends JPanel implements ActionListener{
	private Box baseBox,subBoxH1,subBoxH2,boxV1,boxV2;
	private JTextField tfIP,tfName;
	private JButton btn;
	public String ip;
	public String name;
	private Message message;
	private Request request;
	private Response response;
	boolean isRegister = false;
	public Register(Message message)
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
		this.message = message;
	}
	public void actionPerformed(ActionEvent e)
	{
		if(isRegister==true)
		{
			JOptionPane.showMessageDialog(this, "����ע�ᡣ");
			return;
		}
		ip = tfIP.getText();
		name = tfName.getText();
		if(ip.length() == 0)
			JOptionPane.showMessageDialog(null, "�����������IP");
		else if(name.length() == 0)
			JOptionPane.showMessageDialog(null, "�������û���");
		else
		{
			request = new Request(1,name);
			message = new Message(ip,request);
			response=message.getResponse();
			if(response!=null && response.getType()==1)
			{
				if(response.getSuccess()==true)
				{
					JOptionPane.showMessageDialog(null, "ע��ɹ���");
					isRegister = true;
				}
				else
					JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�IP/�û����Ѵ���");
			}
		}
	}
}
