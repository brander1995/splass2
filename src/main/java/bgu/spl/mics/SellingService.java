package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.passiveObjects.OrderResult;

public class SellingService extends MicroService  {
	
	private MoneyRegister register;
	
	
	private BookOrderCallback<BookOrderEvent> orderCallback;
	
	
	
	
	public SellingService() {
		super("Service Service");
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
