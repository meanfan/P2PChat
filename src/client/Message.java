package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.Request;

public class Message{
	public boolean register(String ip,String name)
	{
		Request request = new Request(1,name);
		
		return send(ip,request);
	}
	public boolean send(String ip,Request request)
	{
		Socket client=null;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		try {
			client = new Socket(ip,2333);
			System.out.println("connected to "+ip+":2333");
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			out.writeObject(request);
			System.out.println("request sent:"+request);
			client.close();
			return true;
			}
		catch(IOException e) {
			System.out.println("Connect error!");
			return false;
			}
	}
}