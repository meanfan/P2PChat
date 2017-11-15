package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client{
	public static void main(String args[])
	{
		String s=null;
		Socket client=null;
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			client = new Socket("localhost",6666);
			System.out.println("server localhost:6666");
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
		while(true)
			{
				out.writeUTF("Hello");
				System.out.println("msg hello sent");
				s = in.readUTF();
				System.out.println("msg received:"+s);
				break;
			}
			
		}
		catch(IOException e) {System.out.println("Connect error!");}
	}
}