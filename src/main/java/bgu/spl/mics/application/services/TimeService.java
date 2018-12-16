package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */



public class TimeService extends MicroService{
	private static class TimeTickHolder{
		private static TimeService instance= new TimeService();
	}
	// The amount of milliseconds between ticks.
	int tickLength = 0;
	
	// How many ticks till death.
	int amountOfTicks = 0;
	
	
	
	private TimeService() {
		super("Time Service");
		// TODO understand this microservice
	}
	public int getTickLength() {
		return tickLength;
	}


	public void setTickLength(int tickLength) {
		this.tickLength = tickLength;
	}


	public int getAmountOfTicks() {
		return amountOfTicks;
	}


	public void setAmountOfTicks(int amountOfTicks) {
		this.amountOfTicks = amountOfTicks;
	}


	
	//singleton
	public static TimeService getInstance()
	{
		return TimeTickHolder.instance;
	}
	
	


	private int UpdateTicks()
	{
		return this.amountOfTicks--;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
		// We should set a timer that counts back ticks on the amount of time delegated by the input.
	}

}
