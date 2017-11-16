package client;

import java.awt.event.*;
import javax.swing.*;

public class Register extends JPanel implements ActionListener{
	private MainWindow mainWindow;
	private Box baseBox,subBoxH1,subBoxH2,boxV1,boxV2;
	private JTextField tfIP,tfName;
	private JButton btn;
	public String ip;
	public String name;
	public Register()
	{
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("输入服务器IP："));
		boxV1.add(Box.createVerticalStrut(16));
		boxV1.add(new JLabel("输入用户名："));
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
		btn = new JButton("确定");
		btn.addActionListener(this);
		subBoxH2.add(btn);
		baseBox = Box.createVerticalBox();
		baseBox.add(subBoxH1);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(subBoxH2);
		add(baseBox);
		validate();
		
	}
	public void setMainWin(MainWindow m)
	{
		this.mainWindow = m;
	}
	public void actionPerformed(ActionEvent e)
	{
		ip = tfIP.getText();
		name = tfName.getText();
		if(ip.length() == 0)
			JOptionPane.showMessageDialog(mainWindow, "请输入服务器IP！");
		else if(name.length() == 0)
			JOptionPane.showMessageDialog(mainWindow, "请输入用户名！");
	}
}
