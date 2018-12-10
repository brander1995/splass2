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
	
	
	
	private Hashtable<String,MicroServiceList> EventBus;
	private Hashtable<String, MicroServiceList> BroadcastBus;
	private Hashtable<Class<?>, ConcurrentLinkedQueue<Message>> MessageQueueContainer;
	
	
	private  MessageBusImpl() {
		EventBus= new Hashtable<>();
		BroadcastBus= new Hashtable<>();
	
	}
	
	
	
	public static MessageBus getInstance()
	{
		return MessageBusHolder.instance;
	}
	
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		EventBus.get(type.getTypeName()).getMicroServices().add(m);
		
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		BroadcastBus.get(type.getTypeName()).getMicroServices().add(m);

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		ConcurrentLinkedQueue<Message> Messages=  new ConcurrentLinkedQueue<>(); 
		MessageQueueContainer.put(m.getClass(), Messages);

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
