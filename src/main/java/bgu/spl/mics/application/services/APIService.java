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
	
	public APIService() {
		super("API Service");
		orderSchedule= new ConcurrentLinkedQueue<>();

	}

	@Override
	protected void initialize() {
		MessageBusImpl.getInstance().register(this);
		
		this.subscribeCustomerOrderEvent();

		
		
	}
	
	
	private void subscribeCustomerOrderEvent()
	{
		//callBack for customerOrderEvent
		Callback<CustomerOrderEvent> customerOrder= new Callback<CustomerOrderEvent>() {

			@Override
			public void call(CustomerOrderEvent c) {
				
				
					orderSchedule.add(c.getOrder());			
					BookOrderEvent order= new BookOrderEvent(c.getOrder(), "API Service", c.getCustomer());
					Future<OrderReceipt> orderbook=sendEvent(order);
					OrderReceipt receipt= orderbook.get();
					complete(c, orderbook.get());
								
			}
			
			
		};//end of customerOrder
		
		this.subscribeEvent(CustomerOrderEvent.class, customerOrder);
		
	}
}
