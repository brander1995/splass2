package bgu.spl.mics.application.services;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.Callback;
import bgu.spl.mics.DebugInfo;
import bgu.spl.mics.Discount;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.CustomerOrderEvent;
import bgu.spl.mics.application.messages.DiscountBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.die;
import bgu.spl.mics.application.passiveObjects.Customer;
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
	Customer customerConnected;
	ConcurrentLinkedQueue<CustomerOrderEvent> orderSchedule1;
	ConcurrentLinkedQueue<Discount> discountList;
	int Curtick=-1;
	
	/*
	 * So this will be the representation of an order for a single user "Online".
	 * 
	 * */
	
	public APIService(Customer cust,ConcurrentLinkedQueue<CustomerOrderEvent> orderSched,Integer nameNum )
	{
		super("API Service"+nameNum.toString());
		this.customerConnected = cust;
		this.orderSchedule1 = orderSched;
		this.discountList= new ConcurrentLinkedQueue<>();
	}
	
	
	public APIService(Integer nameNum) {
		super("API Service"+nameNum.toString());
		orderSchedule= new ConcurrentLinkedQueue<>();
	}
	
	public Customer getCustomerConnected() {
		return customerConnected;
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
		
//		this.subscribeCustomerOrderEvent();
		this.subscribeDiscount();
		this.SubscribeTimeBroadcast();
		this.subscribeDieBroadcast();
		
		TimeService.getInstance().setReadyState(this.getName());
		
		
	}


	/**
	 * 
	 */
	private void sendTickEvents(AtomicInteger tick) {
		//sends the customer order
		for (CustomerOrderEvent book: orderSchedule1)
		{
			if (book.getTick() == tick.get())
			{
				BookOrderEvent order= new BookOrderEvent(book.getOrder(), "API Service", customerConnected, book.getTick());
				Future<OrderReceipt> orderbook=sendEvent(order);
				DebugInfo.PrintHandle(this.getName() + " using get on OrderRecipt for " + order.getBook());
				OrderReceipt receipt= orderbook.get();
				customerConnected.addReceipt(receipt);
			}
		}
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
	
	private void SubscribeTimeBroadcast()
	{
		Callback<TickBroadcast> tick= new Callback<TickBroadcast>() {

			@Override
			public void call(TickBroadcast c) {
				DebugInfo.PrintTick(c.currentTick());
				Curtick=c.currentTick();
				sendTickEvents(new AtomicInteger(c.currentTick()));
			}
			
			
		};
		
		super.subscribeBroadcast(TickBroadcast.class, tick);
	}
	
	
	// What do we do with this?
//	private void subscribeCustomerOrderEvent()
//	{
//	//callBack for customerOrderEvent
//		Callback<CustomerOrderEvent> customerOrder= new Callback<CustomerOrderEvent>() {
//
//			@Override
//			public void call(CustomerOrderEvent c) {
//				
//					int orderTick=Curtick;
//					orderSchedule.add(c.getOrder());		
//					
//					//sends the customer order
//					BookOrderEvent order= new BookOrderEvent(c.getOrder(), "API Service", customerConnected,orderTick);
//					Future<OrderReceipt> orderbook=sendEvent(order);
//					OrderReceipt receipt= orderbook.get();
//					
//					//sends the  receipt as a result 
//					complete(c, orderbook.get());
//								
//			}
//			
//			
//		};//end of customerOrder
//		
//		this.subscribeEvent(CustomerOrderEvent.class, customerOrder);
//
//	}
	
	
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
