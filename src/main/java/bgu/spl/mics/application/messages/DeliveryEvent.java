package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class DeliveryEvent implements  Event<Boolean>{
	
	private String Address;
	private int distance;
	private String senderName;
	
	
	public DeliveryEvent(String address, int diss, String sender)
	{
		this.Address=address;
		this.distance=diss;
		this.senderName=sender;
	}
	
	
	public String getAddress()
	{
		return this.Address;
	}
	
	public int getDistance()
	{
		return this.distance;
	}
	
	public String getSender()
	{
		return this.senderName;
	}
	
	

}
