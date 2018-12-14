package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

public class DeliveryEvent implements  Event<Boolean>{
	
	private String Address;
	private int distance;
	private String senderName;
	private DeliveryVehicle vehicle;
	
	
	public DeliveryEvent(String address, int diss, String sender ,DeliveryVehicle v)
	{
		this.Address=address;
		this.distance=diss;
		this.senderName=sender;
		this.vehicle=v;
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
	
	public DeliveryVehicle getVehicle()
	{
		return this.vehicle;
	}
	
	

}
