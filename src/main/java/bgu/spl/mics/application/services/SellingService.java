package bgu.spl.mics.application.services;

import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.ChackAvailabilityEvent;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.die;
import bgu.spl.mics.application.messages.resourceEvent;

import java.util.concurrent.TimeUnit;

import bgu.spl.mics.Callback;
import bgu.spl.mics.DebugInfo;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
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
	int Curtick=-1;
	private String name;
	
	public SellingService(Integer nameNum) 
	{
		super("Selling Service"+nameNum.toString());
		this.register=MoneyRegister.getInstance();
		this.name="Selling Service"+nameNum.toString();
	}
	
	


	
	
	@Override
	protected void initialize() {
		
		
		MessageBusImpl.getInstance().register(this);
		
		this.subscribeBookOrderEvent();
		
		
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
	
	
	private void subscribeBookOrderEvent()
	{
		
		//orderBook callBack as anonymous class
		Callback<BookOrderEvent> orderCallback= new  Callback<BookOrderEvent>() {

			@Override
			public void call(BookOrderEvent c) {
					int ProccessTick=Curtick;
					ChackAvailabilityEvent check = new ChackAvailabilityEvent(c.getBook(),"Selling Service");
					Future<Integer> availability = sendEvent(check);
					if (availability == null)
					{
						complete(c, null);
						return;
					}
					
					DebugInfo.PrintHandle(getName() + " using get on ChakAvalibiltyEvent for  " + c.getBook());
					Integer result= availability.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS);
					if ((result != null) && (result > 0) && (c.getCustomer().getAvailableCreditAmount() > result))
					{
						//if available - try to take the book from the inventory
						TakeBookEvent buy= new TakeBookEvent(c.getBook(),"Selling Service",c.getCustomer().getId(),c.getOtderTick(),ProccessTick);
						Future<OrderReceipt> Order= sendEvent(buy);
						if (Order==null)
						{
							complete(c, null);
							return;
						}
						//if "takeBook" didn't succeed- return null as a result
						DebugInfo.PrintHandle(getName() + "using get for an orderRecipt" + buy.getBook());
						if (Order.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS) == null)
						{
							complete(c,null);
						}
						else 
						{
							
						//if TakeBook succeed - deliver the book to the customer		
						resourceEvent deliver= new resourceEvent(name,c.getCustomer().getAddress(),c.getCustomer().getDistance());
						Future<Boolean> delivery= sendEvent(deliver);
						
						if (delivery == null)
						{
							complete(c,null);
							return;
						}
						
						DebugInfo.PrintHandle(getName() + " using get for the deliver " + deliver.getAddress() + deliver.getSender()); 
						Boolean delivaryResult=delivery.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS);;
						
						if ((delivaryResult != null) && (delivaryResult == true))
						{
							//if delivery succeed- charge customer and send receipt
							int toCharge= Order.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS).getPrice();//the price in the receipt is the price after discount

							register.chargeCreditCard(c.getCustomer(), toCharge);
							
							// Adding receipt to the register
							register.file(Order.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS));
							
							complete(c, Order.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS));// sends receipt as a result
						}
						//if delivery failed- return null as a result
						else
						{
							complete(c, null);
						}	
						}
						
					}
						
					//if book isn't available -return null as a result
					else
					{
						// TODO Removeme!
						try {
						DebugInfo.PrintHandle("result is now " + result.toString() + " For Cust : " + c.getCustomer().toString());
						DebugInfo.PrintHandle("So the customer could not complete the purchase");
						}
						catch (Exception e) {
							// TODO: handle exception
						}
						complete(c, null);
					}
					
				}
				
			
			
		};//end of orderCallback
		
		
		
		super.subscribeEvent(BookOrderEvent.class, orderCallback);

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

	

}
