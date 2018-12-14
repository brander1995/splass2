package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class ChackAvailabilityEvent implements Event<Boolean>{
	
	private String bookName;
	private String senderName;
	
	public ChackAvailabilityEvent(String book ,String Sender)
	{
		this.bookName=book;
		this.senderName=Sender;
	}


		
	public String getName()
	{
		return this.bookName;
	}
	
	
	public String getSender()
	{
		return this.senderName;
	}

}
