package client;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Toolkit;
public class MainWindow extends JFrame{
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 400;
    public int windowsHeight = 350;
    private Register register;
    public MainWindow() {
    	this.setTitle("P2PChat");
        this.setVisible(true);
        this.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
        JTabbedPane p=new JTabbedPane(JTabbedPane.TOP);
        //TODO 添加选项卡各组件 p.add(名称,组件);
        
        register =  new Register();
        register.setMainWin(this);
        p.add("注册",register);
        
        p.add("聊天", new JLabel("null"));
        p.add("关于", new JLabel("Copywrong (￠) &#$@##@, No Rights Reserved."));
        
        p.validate();
        this.add(p,BorderLayout.CENTER);
        this.validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	public static void main(String[] args) {
		new MainWindow();
	}

}