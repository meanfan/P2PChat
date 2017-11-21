package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;

import data.Request;
import data.Response;

public class Server{
	HashMap<String,InetAddress> table;//<name,ip>
	HashMap<InetAddress,String> table2;//<ip,name>
	Request request;
	ServerSocket serverSocket;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	public Server()
	{
		table = new HashMap<String,InetAddress>();
		table2 = new HashMap<InetAddress,String>();
		try {
			serverSocket = new ServerSocket(2333);
			System.out.println("Opened port:2333");
		}
		catch(IOException e1) {e1.printStackTrace();}
		try {
			while(true)
			{
				socket = serverSocket.accept();
				System.out.println("connect from:"+socket.getInetAddress());
				if(socket!=null)
					new ServerThread(table,table2,socket).start();
			}
		}
		catch(IOException e2){e2.printStackTrace();}
	}
	public static void main(String args[])
	{
		new Server();
	}
}
class ServerThread extends Thread{
	HashMap<String,InetAddress> table;
	HashMap<InetAddress,String> table2;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	Request request;
	Response response;
	ServerThread(HashMap<String,InetAddress> t,HashMap<InetAddress,String> t2,Socket s)
	{
		table = t;
		table2 =t2;
		socket = s;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException e) {System.out.println(""+e);}
	}
	void MessageHandler()
	{
		if(request!=null)
		{
			switch(request.getType())
			{
			case Request.TYPE_REGISTER:
				if(table.containsKey(request.getName()) || table2.containsKey(socket.getInetAddress()))
				{
					System.out.println("registe refused,name existed:"+request.getName()+"|"+socket.getInetAddress());
					response = new Response(Response.TYPE_REGISTER,Response.REGISTER_FAIL);
				}
				else
				{
					table.put(request.getName(),socket.getInetAddress());
					table2.put(socket.getInetAddress(),request.getName());
					System.out.println("new register:"+request.getName()+"|"+socket.getInetAddress());
					System.out.println(table.toString());
					response = new Response(Response.TYPE_REGISTER,Response.REGISTER_SUCCESS);
				}
				try {
					out.writeObject(response);
				}catch(IOException e) {e.printStackTrace();}
				break;
			case Request.TYPE_USER_LIST:
				response = new Response(Response.TYPE_USERS_LIST,table);
				try {
					out.writeObject(response);
					System.out.println("table sent");
				}catch(IOException e) {e.printStackTrace();}
				break;
			}
		}
	}
	public void run() {
		while(true)
		{
			try {
				request = (Request)in.readObject();
				System.out.println("Request got:"+request);
				MessageHandler();
			}catch(SocketException e1)
			{
				System.out.println("User quit:"+socket.getInetAddress());
				table.remove(table2.get(socket.getInetAddress()));
				table2.remove(socket.getInetAddress());
				break;
			}catch(ClassNotFoundException e) {
				break;
			}
			catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}