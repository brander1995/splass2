package bgu.spl.mics.application.messages;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class CustomerOrderEvent implements Event<OrderReceipt>{
	
	private String book;
	private String SenderName;
	private Customer customer;
	
	
	public CustomerOrderEvent(String Sender, Customer c, String BookName)
	{
		this.book=BookName;
		this.SenderName=Sender;
		this.customer=c;
	}
	

	
	public String getOrder()
	{
		return this.book;
	}
	
	public Customer getCustomer()
	{
		return this.customer;
	}
	
	
	public String getSender()
	{
		return this.SenderName;
	}


}
