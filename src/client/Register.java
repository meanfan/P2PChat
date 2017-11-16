package client;

import javax.swing.*;

public class Register extends JPanel{
	Box baseBox,subBoxH1,subBoxH2,boxV1,boxV2;
	public Register()
	{
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("输入服务器IP："));
		boxV1.add(Box.createVerticalStrut(16));
		boxV1.add(new JLabel("输入用户名："));
		boxV2 = Box.createVerticalBox();
		boxV2.add(new JTextField(16));
		boxV2.add(Box.createVerticalStrut(16));
		boxV2.add(new JTextField(16));
		subBoxH1 = Box.createHorizontalBox();
		subBoxH1.add(boxV1);
		subBoxH1.add(Box.createHorizontalStrut(20));
		subBoxH1.add(boxV2);
		subBoxH2 = Box.createVerticalBox();
		subBoxH2.add(new JButton("确定"));
		baseBox = Box.createVerticalBox();
		baseBox.add(subBoxH1);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(subBoxH2);
		add(baseBox);
		validate();
		
	}
}
