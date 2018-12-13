package bgu.spl.mics.application.messages;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class CustomerOrderEvent implements Event<OrderReceipt>{
	
	private ConcurrentLinkedQueue<String> cOrder;
	
	public CustomerOrderEvent()
	{
		cOrder=new ConcurrentLinkedQueue<>();
	}
	
	public void insertOrder (String bookName)
	{
		cOrder.add(bookName);
	}
	
	public ConcurrentLinkedQueue<String> getOrder()
	{
		return this.cOrder;
	}

}
