package bgu.spl.mics.application.messages;


import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

public class resourceEvent implements Event <Boolean>{
	
	String sender;
	private String Address;
	private int distance;
	
	
	public resourceEvent( String Sender, String add, int dis)
	{
		this.sender=Sender;
		this.Address=add;
		this.distance=dis;
	}
	

	
	public String getSender()
	{
		return this.sender;
	}
	
	public String getAddress()
	{
		return this.Address;
	}
	
	public int getDistance()
	{
		return this.distance;
	}
	
	
	

	

}
