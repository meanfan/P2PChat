package data;

import java.io.Serializable;
import java.util.Vector;

public class Response implements Serializable{
	private int type;
	private boolean success;
	private String message;
	private Vector<String> nameList;
	private Vector<String> ipList;
	
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
	public Response(int type,Vector<String> nameList,Vector<String> ipList)
	{
		this(type);
		this.nameList = nameList;
		this.ipList = ipList;
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
	public Vector<String> getNameList()
	{
		return nameList;
	}
	public Vector<String> getIpList()
	{
		return ipList;
	}
	
	
}
