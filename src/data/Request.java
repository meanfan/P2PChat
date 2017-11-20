package data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/*
 * ÇëÇóµÄ·â×°
 */
public class Request implements Serializable{
	public static final int TYPE_REGISTER = 1;
	public static final int TYPE_USER_LIST = 2;
	public static final int TYPE_CHAT_REQUEST = 3;
	public static final int TYPE_CHAT_REQUEST_ACCECPTED = 4;
	public static final int TYPE_CHAT_REQUEST_REFUSED = 5;
	public static final int TYPE_CHAT_MSEEAGE = 6;
	public static final int TYPE_CHAT_QUIT = 7;
	
	private int type;
	private String name;
	private String message;
	public Request(int type)
	{
		this.type = type;
	}
	public Request(int type,String str)
	{
		this(type);
		if(type == TYPE_REGISTER)
			this.name = str;
		if(type == TYPE_CHAT_REQUEST)
			this.name = str;
		else if(type == TYPE_CHAT_MSEEAGE)
			this.message = str;
	}
	
	public int getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}
	public String getMessage()
	{
		return message;
	}
	public byte[] toByte()
	{
		ByteArrayOutputStream bo = new ByteArrayOutputStream(); 
		ObjectOutputStream oo;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
		} catch (IOException e) {e.printStackTrace();} 
		return bo.toByteArray();
	}
	public static Request toRequest(byte b[])
	{
		ByteArrayInputStream bi = new ByteArrayInputStream(b); 
		ObjectInputStream oi;
		try {
			oi = new ObjectInputStream(bi);
			return (Request)oi.readObject();
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (IOException e1) {e1.printStackTrace();}
		return null;
	}
}
