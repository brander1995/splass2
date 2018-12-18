package bgu.spl.mics;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MicroServiceList {
	
	
	
	private ConcurrentLinkedQueue<MicroService> microservices;
	
	
	public MicroServiceList()
	{
		microservices=new ConcurrentLinkedQueue<>();
	
	}
	
	
	
	public ConcurrentLinkedQueue<MicroService> getMicroServices()
	{
		return this.microservices;
	}
	
	
	

	
	
	
}
