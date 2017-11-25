package data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;
/*
 * 回复的封装
 */
public class Response implements Serializable{
	public static final int TYPE_REGISTER = 1;
	public static final int TYPE_USERS_LIST = 2;
	public static final boolean REGISTER_SUCCESS = true;
	public static final boolean REGISTER_FAIL = false;
	private int type;
	private boolean success;
	private HashMap<String,InetAddress> table;
	private String message;
	public Response(int type)
	{
		this.type = type;
	}
	public Response(int type,boolean success)
	{
		this(type);
		this.success = success;
	}
	public Response(int type,String message)
	{
		this(type);
		this.message = message;
	}
	public Response(int type,HashMap<String,InetAddress> table)
	{
		this(type);
		this.table = table;
	}
	
	public int getType()
	{
		return type;
	}
	public boolean getSuccess()
	{
		return success;
	}
	public String getMessage()
	{
		return message;
	}
	public HashMap<String,InetAddress> getTable()
	{
		return table;
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
	public static Response toResponse(byte b[])
	{
		ByteArrayInputStream bi = new ByteArrayInputStream(b); 
		ObjectInputStream oi;
		try {
			oi = new ObjectInputStream(bi);
			return (Response)oi.readObject();
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (IOException e1) {e1.printStackTrace();}
		return null;
	}
}
