package client;
/*
 * 主窗口类
 */
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class MainWin extends JFrame{
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 400;
    public int windowsHeight = 350;
    private Register register;
    private ChatTable chatTable;
    public UDPMessageListener msgListener;
    public MainWin() {
    	this.setTitle("P2PChat");
        this.setVisible(true);
        this.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
        JTabbedPane p=new JTabbedPane(JTabbedPane.TOP);
        //TODO 添加选项卡各组件 p.add(名称,组件);
        msgListener = new UDPMessageListener(2333);
        register =  new Register(msgListener);
        p.add("注册",register);
        chatTable = new ChatTable(register);
        p.add("聊天", chatTable);
        p.add("关于", new JLabel("Copywrong (￠) &#$@##@, No Rights Reserved."));
        p.validate();
        this.add(p,BorderLayout.CENTER);
        this.validate();
        addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e) {
        		close();
        	}
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public void close()
    {
    	
    	msgListener.close();
    	System.exit(0);
    }
	public static void main(String[] args) {
		new MainWin();
	}

}