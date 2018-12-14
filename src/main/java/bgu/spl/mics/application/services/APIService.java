package bgu.spl.mics.application.services;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.CustomerOrderEvent;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

/**
 * APIService is in charge of the connection between a client and the store.
 * It informs the store about desired purchases using {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class APIService extends MicroService{

	ConcurrentLinkedQueue<String> orderSchedule;
	
	public APIService(ConcurrentLinkedQueue<String> orderList) {
		super("API Service");
		orderList= new ConcurrentLinkedQueue<>();
		for (String order:orderList)
			orderSchedule.add(order);
	}

	@Override
	protected void initialize() {
		MessageBusImpl.getInstance().register(this);
		
		//TODO: choose option (i think they want option 2)
		//option 1:
		this.subscribeCustomerOrderEvent();

		
		//option 2:
		for (String book: this.orderSchedule)
		{
			BookOrderEvent order= new BookOrderEvent(book, "API Service");
			Future<OrderReceipt> orderbook=sendEvent(order);
			OrderReceipt receipt= orderbook.get();
			//TODO reslove
		}
		
		
		//this class need to subscribe to customer Order event and send Book order event. 
		
		/*
		 * send order book event
		 * give the customer the orderReceipt 
		 * this class is responsible for sending Delivery Event?
		 * 
		 */
		
		
	}
	
	
	//TODO do i need to subscribe to this event? we received the order in the constructor. 
	private void subscribeCustomerOrderEvent()
	{
		//callBack for customerOrderEvent
		Callback<CustomerOrderEvent> customerOrder= new Callback<CustomerOrderEvent>() {

			@Override
			public void call(CustomerOrderEvent c) {
				
				for (String book: c.getOrder())
				{
					orderSchedule.add(book);			
					BookOrderEvent order= new BookOrderEvent(book, "API Service");
					Future<OrderReceipt> orderbook=sendEvent(order);
					OrderReceipt receipt= orderbook.get();
					//TODO reslove
				}
				
			}
			
			
		};//end of customerOrder
		
		this.subscribeEvent(CustomerOrderEvent.class, customerOrder);
		
	}
}
