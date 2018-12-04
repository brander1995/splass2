package bgu.spl.mics;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MicroServiceList<T> {
	
	
	
	private ConcurrentLinkedQueue<MicroService> microservices;
	private ConcurrentLinkedQueue<Event<T>> events;
	
	
	public MicroServiceList()
	{
		microservices=new ConcurrentLinkedQueue<>();
		events= new ConcurrentLinkedQueue<>();
	}
	
	
	
	public ConcurrentLinkedQueue<MicroService> getMicroServices()
	{
		return this.microservices;
	}
	
	
	
	public ConcurrentLinkedQueue<Event<T>> getEvents()
	{
		return this.events;
	}
	
	
	
}
