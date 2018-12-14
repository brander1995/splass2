package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.passiveObjects.OrderResult;

public class BookOrderEvent implements Event<OrderReceipt> {
	
	private String bookName;
	private String senderName;
	
	public BookOrderEvent(String book, String Sender)
	{
		this.bookName=book;
		this.senderName=Sender;
	}
	
	
	public String getBook()
	{
		return this.bookName;
	}
	
	
	public String getSenderName()
	{
		return this.senderName;
	}
	
	

}
