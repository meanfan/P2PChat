package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
	public static void main(String args[])
	{
		String s=null;
		ServerSocket serverSocket=null;
		Socket server=null;
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			serverSocket = new ServerSocket(6666);
			System.out.println("serverSocket port 6666");
		}
		catch(IOException e1) {
			System.out.println("ERROR:"+e1);
		}
		try {
			server = serverSocket.accept();
			System.out.println("server socket acceped");
			in = new DataInputStream(server.getInputStream());
			out = new DataOutputStream(server.getOutputStream());
			while(true)
			{
				s = in.readUTF();
				out.writeUTF("hi");
				System.out.println("msg hi sent");
				System.out.println("msg received:" +s);
				break;
			}
				
		}catch(IOException e1){
			System.out.println(""+e1);
		}
	}
}