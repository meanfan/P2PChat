package client;

import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChatWin extends JFrame{
	private String myName;
	private String yourName;
	private InetAddress ip;
	public ChatWin(String myName,String yourName,InetAddress ip)
	{
		this.myName = myName;
		this.yourName = yourName;
		this.ip = ip;
		//TODO ¡ƒÃÏ«Î«Û
		JOptionPane.showMessageDialog(null, "...");
		//
		
	}
}
