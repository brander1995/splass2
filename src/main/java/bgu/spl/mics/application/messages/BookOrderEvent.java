package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.passiveObjects.OrderResult;

public class BookOrderEvent implements Event<OrderReceipt> {
	
	private String bookName;
	private String senderName;
	private Customer customer;
	
	public BookOrderEvent(String book, String Sender , Customer  c)
	{
		this.bookName=book;
		this.senderName=Sender;
		this.customer=c;
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
