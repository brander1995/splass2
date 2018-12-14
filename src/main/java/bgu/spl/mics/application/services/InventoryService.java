package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.ChackAvailabilityEvent;
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
	
	
	public InventoryService(String Book) {
		super("Invemtory Service");
		this.inventory=Inventory.getInstance();
		this.bookName=Book;
	}

	@Override
	protected void initialize() {
		
		MessageBusImpl.getInstance().register(this);
		
		Callback<ChackAvailabilityEvent> checkCallBack= new  Callback<ChackAvailabilityEvent>() {

			@Override
			public void call(ChackAvailabilityEvent c) {
				
				if (inventory.checkAvailabiltyAndGetPrice(bookName)!=-1)
				{
					//resolve with the result available . how??? should i give future as a parameter to the class?
				}
			}
			
		};

		super.subscribeEvent(ChackAvailabilityEvent.class, checkCallBack);
		
		Callback<TakeBookEvent> takeBookCallBack= new Callback<TakeBookEvent>() {

			@Override
			public void call(TakeBookEvent c) {
				// TODO Auto-generated method stub
				
			}
		};
		
		super.subscribeEvent(TakeBookEvent.class, takeBookCallBack);
		
		//this class need to subscribe to:
		//1. check availability event and send a result- true or false
		//2. take book
		
		
		/*
		 * checks availability via Inventory class
		 * return the result true if available or false other wise (future)
		 *  
		 */
	}
	

}
