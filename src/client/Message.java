package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import data.Request;
import data.Response;

public class Message{
	private String ip;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Request request;
	private Response response;
	
	public boolean connect(String ip,Request request)
	{
		this.ip = ip;
		this.request = request;
		try {
			socket = new Socket(ip,2333);
			System.out.println("connected to "+ip+":2333");
		}catch(IOException e1) {
			e1.printStackTrace();
			return false;
		}
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e2) {
			e2.printStackTrace();
			return false;
		}
		return true;
	}
	public Response getResponse()
	{
		return response;
	}
	Message(String ip,Request request)
	{
		if(connect(ip,request)==false)
			JOptionPane.showMessageDialog(null, "服务器连接失败！");
		else
		{
			try {
				out.writeObject(request);
				System.out.println("request sent:"+request);
			}catch(IOException e1) {e1.getStackTrace();}
			try {
				response = (Response)in.readObject();
			}catch (IOException e2) {e2.printStackTrace();}
			catch (ClassNotFoundException e3) {e3.printStackTrace();}
		}
	}

}