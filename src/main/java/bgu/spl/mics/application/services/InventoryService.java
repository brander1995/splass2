package bgu.spl.mics.application.services;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Discount;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.ChackAvailabilityEvent;
import bgu.spl.mics.application.messages.DiscountBroadcast;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

/**
 * InventoryService is in charge of the book inventory and stock.
 * Holds a reference to the {@link Inventory} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class InventoryService extends MicroService{

	Inventory inventory;
	ConcurrentLinkedQueue<Discount> discountList;
	int orderId;
	int Curtick=-1;
	
	
	public InventoryService(Integer nameNum) {
		super("Invemtory Service"+nameNum.toString());
		this.inventory=Inventory.getInstance();
		this.discountList= new ConcurrentLinkedQueue<>();
		this.orderId=0;

	}
	
	
	private void setDiscount (int dis, String book)
	{
		boolean flag=false;
		for (Discount b:discountList)
		{
			if (b.getBook().equals(book))
			{
				b.setDiscount(dis);
				flag=true;
			}
		}
		if (flag==false)
			discountList.add(new Discount(book, dis));
	}
	


	
	@Override
	protected void initialize() {
		
		MessageBusImpl.getInstance().register(this);
		
		this.subscribeCheckAvailability();

		this.subscribeTakeBook();
		
		this.subscribeDiscount();
		
		this.SubscribeTimeBroadcast();

	}
	
	
	private void SubscribeTimeBroadcast()
	{
		Callback<TickBroadcast> tick= new Callback<TickBroadcast>() {

			@Override
			public void call(TickBroadcast c) {
				Curtick=c.currentTick();
				
			}
			
			
		};
		
		super.subscribeBroadcast(TickBroadcast.class, tick);
	}
	
	
	private void subscribeCheckAvailability()
	{
		Callback<ChackAvailabilityEvent> checkCallBack= new  Callback<ChackAvailabilityEvent>() {

			@Override
			public void call(ChackAvailabilityEvent c) {
				
				
				
				if (inventory.checkAvailabiltyAndGetPrice(c.getName())!=-1)
				{
					//if available- result is true 
					complete(c, true);
				}
				else
				{
					//if not available- result is false
					complete(c, false);
				}
			}
			
		};

		super.subscribeEvent(ChackAvailabilityEvent.class, checkCallBack);
	}
	
	
	
	private void subscribeTakeBook()
	{
		
		Callback<TakeBookEvent> takeBookCallBack= new Callback<TakeBookEvent>() {

			@Override
			public void call(TakeBookEvent c) {
				
				inventory.take(c.getBook());
				int price=inventory.checkAvailabiltyAndGetPrice(c.getBook());
				for (Discount dis: discountList)
				{
					if (dis.getBook().equals(c.getBook()))
						price=price*(dis.getDiscount()/100);
				}
				OrderReceipt receipt=new OrderReceipt(c.getBook(), price,c.getCustomerId() , Curtick, c.getOrderTick(), c.getProccessTick(), orderId, c.getSender());
				orderId++;
				complete(c, receipt);
				
			}
		};
		
		super.subscribeEvent(TakeBookEvent.class, takeBookCallBack);
		
		
	}
	
	
	private void subscribeDiscount()
	{
		//discount CallBack as anonymous class
		Callback<DiscountBroadcast> discount= new Callback<DiscountBroadcast>() {

			@Override
			public void call(DiscountBroadcast c) {
				
				setDiscount(c.getPercent(), c.getBook());
				
			}
			
		};// end of discount
		
		
		super.subscribeBroadcast(DiscountBroadcast.class, discount);
	}
	
	
	
	
	
	

	//this class need to subscribe to:
	//1. check availability event and send a result- true or false
	//2. take book
	//3. discount TODO discount
	
	
	

}
