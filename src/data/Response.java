package data;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;

public class Response implements Serializable{
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
}
