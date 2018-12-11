package bgu.spl.mics;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private static class MessageBusHolder{
		private static MessageBus instance= new MessageBusImpl();
	}
	
	
	// The microservices for each type of event, using getTypeName or what not.
	private Hashtable<String, MicroServiceList> EventBus;
	private Hashtable<String, MicroServiceList> BroadcastBus;
	
	// The messages for each queue? so the container for them.
	private Hashtable<Class<?>, ConcurrentLinkedQueue<Message>> MessageQueueContainer;
	
	// Future to event -> Links a future to the corresponding event, needed 
	// for complete and sendEvent, so we would know who did what.
	private ConcurrentLinkedQueue<EventToFuture> eventAndFuture;
	 
	private Object key1;
	
	private  MessageBusImpl() {
		EventBus= new Hashtable<>();
		BroadcastBus= new Hashtable<>();
		MessageQueueContainer= new Hashtable<>();
		eventAndFuture= new ConcurrentLinkedQueue<>();
	
	}
	
	
	
	public static MessageBus getInstance()
	{
		return MessageBusHolder.instance;
	}
	
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		EventBus.get(type.getClass().getName()).getMicroServices().add(m);
		
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		BroadcastBus.get(type.getClass().getName()).getMicroServices().add(m);

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		for (EventToFuture event: eventAndFuture)
		{
			Future<T> releventFuture =(Future<T>) event.getReleventFuture(e);
			if (releventFuture != null)
			{
				releventFuture.resolve(result);
			}
		}
		
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		
		// See explanation below
		synchronized (key1) {
			MicroServiceList MicroServises= BroadcastBus.get(b.getClass().getName());
			if (MicroServises==null)
			{
				return;
			}
			for (MicroService micro: MicroServises.getMicroServices())
			{
				MessageQueueContainer.get(micro.getClass()).add(b);
			}	
		
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		
		/*  Locking for the following scenario
			if we got an event and a broadcast at the same time,
			The first event will be updated,
			then removed
			then added again.
			
			in the same time, the broadcast will update the first event,
			and might update it again the second time.
			
			To prevent this, locking the queues so that only one can use them at a time. 
		*/
		synchronized (key1) {

			// Get the relevent microservice queue
			ConcurrentLinkedQueue<MicroService> micro = EventBus.get(e.getClass().getName()).getMicroServices();
			
			if (micro==null)
			{
				return null;
			}
			
			
			// Use the first microservice to handle the event, then return it to the bottom of the list
			MicroService m=micro.remove();
			MessageQueueContainer.get(m.getClass()).add(e);
			micro.add(m);
			
			Future<T> returnValue = new Future<>();
			EventToFuture ftLinker = new EventToFuture(returnValue, e);
			
			// Link the future to the event, so we can use it in the completion 
			this.eventAndFuture.add(ftLinker);
			
			return returnValue;
	
		}
	}

	
	
	@Override
	public void register(MicroService m) {
		ConcurrentLinkedQueue<Message> Messages=  new ConcurrentLinkedQueue<>(); 
		MessageQueueContainer.put(m.getClass(), Messages);

	}

	@Override
	public void unregister(MicroService m) {
		
		for (MicroServiceList microList: EventBus.values())
		{
			for (MicroService micro: microList.getMicroServices())
			{
				if (micro.equals(m))
				{
					microList.getMicroServices().remove(micro);
				}
			}
			
		}
		
		
		for (MicroServiceList microList: BroadcastBus.values())
		{
			for (MicroService micro: microList.getMicroServices())
			{
				if (micro.equals(m))
				{
					microList.getMicroServices().remove(micro);
				}
			}
		}
		
		
		MessageQueueContainer.remove(m.getClass());
		
	}
	 

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		ConcurrentLinkedQueue<Message> serMessageQueue = this.MessageQueueContainer.get(m.getClass());
		
		if (!serMessageQueue.isEmpty())
		{
			return serMessageQueue.remove();
		}
		
		return null;
	}

	

}
