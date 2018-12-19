package bgu.spl.mics.application.services;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Callback;
import bgu.spl.mics.DebugInfo;
import bgu.spl.mics.Discount;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.ChackAvailabilityEvent;
import bgu.spl.mics.application.messages.DiscountBroadcast;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.die;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.passiveObjects.OrderResult;

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
		
		this.subscribeDieBroadcast();
		
		TimeService.getInstance().setReadyState(this.getName());

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
				
				complete(c, inventory.checkAvailabiltyAndGetPrice(c.getName()));
			}
			
		};

		super.subscribeEvent(ChackAvailabilityEvent.class, checkCallBack);
	}
	
	
	
	private void subscribeTakeBook()
	{
		
		Callback<TakeBookEvent> takeBookCallBack= new Callback<TakeBookEvent>() {

			@Override
			public void call(TakeBookEvent c) {
				
				if (inventory.take(c.getBook())==OrderResult.NOT_IN_STOCK)
				{
					complete(c, null);
				}
				else
				{
					int price=inventory.checkAvailabiltyAndGetPrice(c.getBook());
					for (Discount dis: discountList)
					{
						if (dis.getBook().equals(c.getBook()))
							price=price*(dis.getDiscount()/100);
					}
					OrderReceipt receipt=new OrderReceipt(c.getBook(), price,c.getCustomerId() , Curtick, c.getOrderTick(), c.getProccessTick(), orderId, c.getSender());
					orderId++;
					DebugInfo.PrintHandle("Sending recipt for completed p");
					complete(c, receipt);
				}
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
	
	
	private void subscribeDieBroadcast()
	{
		Callback<die> terminate= new Callback<die>() {
			
			@Override
			public void call(die c) {
				if(c.getTerminate())
					terminate();
					
				
			}
		};
			
		subscribeBroadcast(die.class, terminate);

	}
	
	
	
	
	
	

	//this class need to subscribe to:
	//1. check availability event and send a result- true or false
	//2. take book
	//3. discount TODO discount
	
	
	

}
