package bgu.spl.mics;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

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
