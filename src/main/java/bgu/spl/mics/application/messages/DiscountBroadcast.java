package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class DiscountBroadcast implements Broadcast{
	
	private int percentDiscount;
	private String bookName;
	private String senderName;
	
	public DiscountBroadcast(int percent, String sender, String book)
	{
		this.percentDiscount=percent;
		this.senderName=sender;
		this.bookName=book;
	}
	
	public int getPercent()
	{
		return this.percentDiscount;
	}
	
	
	public String getSender()
	{
		return this.senderName;
	}
	
	
	public String getBook()
	{
		return this.bookName;
	}
	
	
	

}
