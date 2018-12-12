package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

public class APIService extends MicroService{

	
	//String for the name of the book?
	ConcurrentLinkedQueue<String> orderSchedule;
	
	
	public APIService() {
		super("API Service");
		orderSchedule= new ConcurrentLinkedQueue<>();
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

}
