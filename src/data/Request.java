package data;

public class Request {
	private int type;
	private String myName;
	private String yourName;
	
	public Request(int type,String myName)
	{
		this.type = type;
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
