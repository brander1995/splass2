package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.ChackAvailabilityEvent;
import bgu.spl.mics.application.messages.DiscountBroadcast;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.passiveObjects.Inventory;

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
	String bookName;
	int discount;
	
	
	public InventoryService() {
		super("Invemtory Service");
		this.inventory=Inventory.getInstance();
		this.bookName="";
		this.discount=0;
	}
	
	
	private void setDiscount (int dis)
	{
		this.discount=dis;
	}
	
	private void setBookName(String name)
	{
		this.bookName=name;
	}

	@Override
	protected void initialize() {
		
		MessageBusImpl.getInstance().register(this);
		
		this.subscribeCheckAvailability();

		this.subscribeTakeBook();
		
		this.subscribeDiscount();

	}
	
	
	private void subscribeCheckAvailability()
	{
		Callback<ChackAvailabilityEvent> checkCallBack= new  Callback<ChackAvailabilityEvent>() {

			@Override
			public void call(ChackAvailabilityEvent c) {
				
				//for the discount- im not sure its going to work
				//TODO check this
				setBookName(c.getName());
				
				
				if (inventory.checkAvailabiltyAndGetPrice(bookName)!=-1)
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
				
				
				// TODO Auto-generated method stub
				
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
				
				if(c.getBook().equals(bookName))
					setDiscount(c.getPercent());
				
			}
			
		};// end of discount
		
		
		super.subscribeBroadcast(DiscountBroadcast.class, discount);
	}
	
	
	
	
	
	

	//this class need to subscribe to:
	//1. check availability event and send a result- true or false
	//2. take book
	//3. discount TODO discount
	
	
	

}
