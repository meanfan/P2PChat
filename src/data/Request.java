package data;

import java.io.Serializable;

public class Request implements Serializable{
	private int type;
	private String myName;
	private String yourName;
	public Request(int type)
	{
		this.type = type;
	}
	public Request(int type,String myName)
	{
		this(type);
		this.myName = myName;
	}
	public Request(int type,String myName,String yourName)
	{
		this(type,myName);
		this.yourName = yourName;
	}
	
	public int getType()
	{
		return type;
	}
	public String getMyName()
	{
		return myName;
	}
	public String getYourName()
	{
		return yourName;
	}
}
