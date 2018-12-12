package bgu.spl.mics.application.services;

import bgu.spl.mics.BookOrderCallback;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;

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
	
	
	private BookOrderCallback<BookOrderEvent> orderCallback;
	
	
	
	
	public SellingService() {
		super("Selling Service");
		this.register=MoneyRegister.getInstance();
		this.orderCallback= new BookOrderCallback<>();
	}

	
	
	@Override
	protected void initialize() {
		
		
		MessageBusImpl.getInstance().register(this);
//		super.subscribeEvent(BookOrderEvent.class, orderCallback);
//		super.subscribeBroadcast(DiscountBroadcast.class, callback);
		// TODO Auto-generated method stub
		
	}
	

	

}
