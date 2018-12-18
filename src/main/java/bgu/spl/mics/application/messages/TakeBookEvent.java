package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class TakeBookEvent implements Event<OrderReceipt>{
	
	@Override
	public String toString() {
		return "TakeBookEvent [bookName=" + bookName + ", senderName=" + senderName + ", customerId=" + customerId
				+ ", orderTick=" + orderTick + ", proccessTick=" + proccessTick + "]";
	}

	private String bookName;
	private String senderName;
	private int customerId;
	int orderTick;
	int proccessTick;
	
	public TakeBookEvent(String book, String sender, int cId, int Ordertick, int proccesstick)
	{
		this.bookName=book;
		this.senderName=sender;
		this.customerId=cId;
		this.orderTick=Ordertick;
		this.proccessTick=proccesstick;
	}
	
	
	public String getBook()
	{
		return this.bookName;
				
	}
	
	
	public String getSender()
	{
		return this.senderName;
	}
	
	public int getCustomerId()
	{
		return this.customerId;
	}
	
	public int getOrderTick()
	{
		return this.orderTick;
	}
	
	public int getProccessTick()
	{
		return this.proccessTick;
	}
	

}
