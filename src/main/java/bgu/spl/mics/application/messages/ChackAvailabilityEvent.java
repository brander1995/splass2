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
		
	@Override
	public String toString() {
		return "ChackAvailabilityEvent [bookName=" + bookName + ", senderName=" + senderName + "]";
	}

	/**
	 * Gets the book name
	 * @return string - The name
	 */
	public String getName()
	{
		return this.bookName;
	}
	
	/**
	 * Returns the sender
	 * @return string - the name of the sender
	 */
	public String getSender()
	{
		return this.senderName;
	}

}
