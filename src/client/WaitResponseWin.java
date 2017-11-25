package client;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/*
 * 聊天请求等待回复并处理回复
 */
class WaitResponseWin extends JFrame implements ActionListener,Runnable{
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 300;
    public int windowsHeight = 100;
	private JButton cancel;
	private String myName;
	private String yourName;
	private UDPMessageListener msgListener;//后台UDP消息接收线程
	boolean needAccecpt;
	//设置聊天双方信息
	public void setChatInfo(String myName,String yourName)
	{
		this.myName = myName;
		this.yourName = yourName;
	}
	//初始化并创建等待窗口
	WaitResponseWin(UDPMessageListener msgListener,boolean needAccecpt)
	{
		super("信息");
		setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		JLabel label = new JLabel("等待对方同意...");
		add(label,BorderLayout.CENTER);
		cancel = new JButton("取消");
		cancel.addActionListener(this);
		add(cancel,BorderLayout.SOUTH);
		validate();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.msgListener = msgListener;
		this.needAccecpt = needAccecpt;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancel)//取消按钮事件
		{
			Thread.interrupted();
			this.dispose();
		}
	}
	//通过监听msgListener中的变量needAccecpt来获取收到的回复
	public void run() {
		if(needAccecpt == true)
		{
			while(msgListener.isChatAccecpted == 0)
			{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
			if(msgListener.isChatAccecpted == 1)
			{
				
				ChatWin chatWin = new ChatWin(myName,yourName,msgListener,msgListener.getAddress());
				Thread chatWinThread = new Thread(chatWin);
				chatWinThread.start();
			}else if(msgListener.isChatAccecpted == 2)
				JOptionPane.showMessageDialog(null, "用户"+yourName+"拒绝了你的聊天请求");
		}else
		{
			ChatWin chatWin = new ChatWin(myName,yourName,msgListener,msgListener.getAddress());
			Thread chatWinThread = new Thread(chatWin);
			chatWinThread.start();
		}
		setVisible(false);
		msgListener.isChatAccecpted = 0;
	}
}
