package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import data.Request;
import data.Response;

public class Server{
	HashMap<String,InetAddress> table;//<name,ip>
	Request request;
	ServerSocket serverSocket;
	Socket server;
	ObjectInputStream in;
	ObjectOutputStream out;
	public Server()
	{
		table = new HashMap<String,InetAddress>();
		try {
			serverSocket = new ServerSocket(2333);
			System.out.println("Opened port:2333");
		}
		catch(IOException e1) {e1.printStackTrace();}
		try {
			while(true)
			{
				server = serverSocket.accept();
				System.out.println("connect from:"+server.getInetAddress());
				if(server!=null)
					new ServerThread(table,server).start();
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
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	Request request;
	Response response;
	ServerThread(HashMap<String,InetAddress> t,Socket s)
	{
		table = t;
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
			case 1:
				if(table.containsKey(request.getMyName()))
				{
					System.out.println("registe refused,name existed:"+request.getMyName()+"|"+socket.getInetAddress());
					response = new Response(1,false);
				}
				else
				{
					table.put(request.getMyName(),socket.getInetAddress());
					System.out.println("new register:"+request.getMyName()+"|"+socket.getInetAddress());
					System.out.println(table.toString());
					response = new Response(1,true);
				}
				try {
					out.writeObject(response);
				}catch(IOException e) {e.printStackTrace();}
				break;
			case 2:
				response = new Response(2,table);
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
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
			catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}