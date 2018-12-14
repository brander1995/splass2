package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class TakeBookEvent implements Event<OrderReceipt>{
	
	private String bookName;
	private String senderName;
	
	public TakeBookEvent(String book, String sender)
	{
		this.bookName=book;
		this.senderName=sender;
	}
	
	
	public String getBook()
	{
		return this.bookName;
				
	}
	
	
	public String getSender()
	{
		return this.senderName;
	}
	

}
