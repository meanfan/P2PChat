package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import data.Request;

public class MessageHandler{
	Request request = null;
	ServerSocket serverSocket=null;
	Socket server=null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	public MessageHandler()
	{
		try {
			serverSocket = new ServerSocket(2333);
			System.out.println("Opened port:2333");
		}
		catch(IOException e1) {
			System.out.println("ERROR:"+e1);
		}
		try {
			while(true)
			{
				server = serverSocket.accept();
				System.out.println("connect from:"+server.getRemoteSocketAddress());
				in = new ObjectInputStream(server.getInputStream());
				out = new ObjectOutputStream(server.getOutputStream());
				request = (Request)in.readObject();
				//System.out.println("request received:"+request);
				Thread.sleep(500);
			}
		}
		catch(IOException e1){System.out.println(""+e1);}
		catch(ClassNotFoundException e2) {System.out.println(""+e2);}
		catch(InterruptedException e3) {System.out.println(""+e3);}
	}
	public static void main(String args[])
	{
		new MessageHandler();
	}
}