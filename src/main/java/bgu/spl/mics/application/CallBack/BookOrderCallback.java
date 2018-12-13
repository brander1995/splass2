package bgu.spl.mics.application.CallBack;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.naming.spi.DirStateFactory.Result;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.messages.ChackAvailabilityEvent;
import bgu.spl.mics.application.messages.CustomerOrderEvent;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class BookOrderCallback<T> implements Callback<T> {
	

	@Override
	public void call(T customerOrder) {
		ConcurrentLinkedQueue<String> order= ((CustomerOrderEvent)customerOrder).getOrder();
		for (String book: order)
		{
			ChackAvailabilityEvent check = new ChackAvailabilityEvent(book);
			Future<Boolean> availability = MessageBusImpl.getInstance().sendEvent(check);
			boolean result= availability.get();
			//TODO complete this
		}
		
	}

}
