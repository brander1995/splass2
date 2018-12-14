package bgu.spl.mics.application.messages;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class CustomerOrderEvent implements Event<OrderReceipt>{
	
	private ConcurrentLinkedQueue<String> cOrder;
	private String SenderName;
	
	public CustomerOrderEvent(String Sender)
	{
		cOrder=new ConcurrentLinkedQueue<>();
		this.SenderName=Sender;
	}
	
	public void insertOrder (String bookName)
	{
		cOrder.add(bookName);
	}
	
	public ConcurrentLinkedQueue<String> getOrder()
	{
		return this.cOrder;
	}
	
	/*
	 * do we need any implementation here? in event we need only sender name? (like the example?) 
	 */

}
