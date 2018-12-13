package bgu.spl.mics.application.services;

import bgu.spl.mics.application.CallBack.BookOrderCallback;
import bgu.spl.mics.application.CallBack.DiscountCallBack;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.CustomerOrderEvent;
import bgu.spl.mics.application.messages.DiscountBroadcast;
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
	
	
	private BookOrderCallback<BookOrderEvent> orderCallback;
	private DiscountCallBack<DiscountBroadcast> discount;
	
	private CustomerOrderEvent customerOrder;
	
	
	
	public SellingService(CustomerOrderEvent CustomerOrder) {
		super("Selling Service");
		this.register=MoneyRegister.getInstance();
		this.orderCallback= new BookOrderCallback<>();
		this.discount= new DiscountCallBack<>();
		this.customerOrder = CustomerOrder;
	}

	
	
	@Override
	protected void initialize() {
		
		
		MessageBusImpl.getInstance().register(this);
		super.subscribeEvent(BookOrderEvent.class, this.orderCallback);
		super.subscribeBroadcast(DiscountBroadcast.class, this.discount);
		
	}
	

	

}
