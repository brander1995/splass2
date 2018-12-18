package bgu.spl.mics.application.messages;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class CustomerOrderEvent implements Event<OrderReceipt>{
	
	@Override
	public String toString() {
		return "CustomerOrderEvent [book=" + book + ", SenderName=" + SenderName + ", tick=" + tick + "]";
	}

	private String book;
	private String SenderName;
	
	// Adding the timer for an order
	private Integer tick;
		
	public Integer getTick() {
		return tick;
	}

	public void setTick(Integer tick) {
		this.tick = tick;
	}

	public String getSenderName() {
		return SenderName;
	}

	public void setSenderName(String senderName) {
		SenderName = senderName;
	}
	
	public CustomerOrderEvent(String BookName, Integer startingTick)
	{
		this.book=BookName;
		this.tick = startingTick;
	}
		
	public String getOrder()
	{
		return this.book;
	}

	public String getSender()
	{
		return this.SenderName;
	}
}
