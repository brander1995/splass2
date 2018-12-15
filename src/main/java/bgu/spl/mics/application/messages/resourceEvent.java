package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class resourceEvent implements Event <Boolean>{
	
	private String Address;
	private int distance;
	private String senderName;
	
	
	public resourceEvent(String address, int dis, String sender)
	{
		this.Address=address;
		this.distance=dis;
		this.senderName=sender;
	}
	
	
	public String getAddress()
	{
		return this.Address;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	
	public String getSender()
	{
		return this.senderName;
	}
	

	

}
