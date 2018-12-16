package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.passiveObjects.OrderResult;

public class BookOrderEvent implements Event<OrderReceipt> {
	
	private String bookName;
	private String senderName;
	private Customer customer;
	private int OrderTick;
	
	public BookOrderEvent(String book, String Sender , Customer  c, int tick)
	{
		this.bookName=book;
		this.senderName=Sender;
		this.customer=c;
		
		this.OrderTick = tick;
	}
	
	
	public String getBook()
	{
		return this.bookName;
	}
	
	
	public String getSenderName()
	{
		return this.senderName;
	}
	
	public Customer getCustomer()
	{
		return this.customer;
	}
	
	

}
