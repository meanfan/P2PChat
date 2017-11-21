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
 * ��������ȴ��ظ�������ظ�
 */
class WaitResponseWin extends JFrame implements ActionListener,Runnable{
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 300;
    public int windowsHeight = 100;
	private JButton cancel;
	private String myName;
	private String yourName;
	private UDPMessageListener msgListener;//��̨UDP��Ϣ�����߳�
	boolean needAccecpt;
	//��������˫����Ϣ
	public void setChatInfo(String myName,String yourName)
	{
		this.myName = myName;
		this.yourName = yourName;
	}
	//��ʼ���������ȴ�����
	WaitResponseWin(UDPMessageListener msgListener,boolean needAccecpt)
	{
		super("��Ϣ");
		setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		JLabel label = new JLabel("�ȴ��Է�ͬ��...");
		add(label,BorderLayout.CENTER);
		cancel = new JButton("ȡ��");
		cancel.addActionListener(this);
		add(cancel,BorderLayout.SOUTH);
		validate();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.msgListener = msgListener;
		this.needAccecpt = needAccecpt;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancel)//ȡ����ť�¼�
		{
			Thread.interrupted();
			this.dispose();
		}
	}
	//ͨ������msgListener�еı���needAccecpt����ȡ�յ��Ļظ�
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
				JOptionPane.showMessageDialog(null, "�û�"+yourName+"�ܾ��������������");
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
