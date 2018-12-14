package bgu.spl.mics.application.services;

import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.ChackAvailabilityEvent;
import bgu.spl.mics.application.messages.CustomerOrderEvent;
import bgu.spl.mics.application.messages.DiscountBroadcast;
import bgu.spl.mics.application.messages.TakeBookEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

/**
 * Selling service in charge of taking orders from customers.
 * Holds a reference to the {@link MoneyRegister} singleton of the store.
 * Handles {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class SellingService extends MicroService{
	

	
	private MoneyRegister register;
	private int discount;
	private String book;
	
	
	
	public SellingService() 
	{
		super("Selling Service");
		this.register=MoneyRegister.getInstance();
		this.discount=0;
	}
	
	
	public void setDiscount(int dis)
	{
		this.discount=dis;
	}

	
	
	@Override
	protected void initialize() {
		
		
		MessageBusImpl.getInstance().register(this);
		
		this.subscribeBookOrderEvent();
		
		this.subscribeDiscountBroadcast();
		
		
		
	}
	
	
	
	
	private void subscribeBookOrderEvent()
	{
		
		//orderBook callBack as anonymous class
		Callback<BookOrderEvent> orderCallback= new  Callback<BookOrderEvent>() {

			@Override
			public void call(BookOrderEvent c) {
				
					ChackAvailabilityEvent check = new ChackAvailabilityEvent(book,"Selling Service");
					Future<Boolean> availability = sendEvent(check);
					boolean result= availability.get();
					if (result==true)
					{
						TakeBookEvent buy= new TakeBookEvent(c.getBook(),"Selling Service");
						Future<OrderReceipt> Order= sendEvent(buy);
						int toCharge= Order.get().getPrice();

						register.chargeCreditCard(c.getCustomer(), toCharge);
						
						complete(c, Order.get());
						
					}
					else
					{
						complete(c, null);
					}
					
				}
				
			
			
		};//end of orderCallback
		
		
		
		super.subscribeEvent(BookOrderEvent.class, orderCallback);

	}
	
	
	
	
	private void subscribeDiscountBroadcast()
	{
		//discount CallBack as anonymous class
		Callback<DiscountBroadcast> discount= new Callback<DiscountBroadcast>() {

			@Override
			public void call(DiscountBroadcast c) {
				
				if(c.getBook().equals(book))
					setDiscount(c.getPercent());
				
			}
			
		};// end of discount
		
		
		super.subscribeBroadcast(DiscountBroadcast.class, discount);
	}
	

	

}
