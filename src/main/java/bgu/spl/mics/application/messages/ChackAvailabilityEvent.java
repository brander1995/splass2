package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class ChackAvailabilityEvent implements Event<Boolean>{
	
	private String bookName;
	
	public ChackAvailabilityEvent(String book)
	{
		this.bookName=book;
	}

}
