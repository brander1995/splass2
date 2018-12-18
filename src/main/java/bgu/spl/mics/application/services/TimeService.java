package bgu.spl.mics.application.services;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.die;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

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
	
	int currentTick = 0;
	
	
	// Array that indicates if all the services have been initialized properly
	HashMap<String, Boolean> initArr;
	
	private TimeService() {
		super("Time Service");
		this.initArr = new HashMap<>();
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
	
	public boolean generateInitArray(MicroService[] initArr)
	{
		for (MicroService microService : initArr) {
			if (this.getName() != microService.getName())
				{
					this.initArr.put(microService.getName(), false);
				}
		}
		return true;
	}
	
	public boolean setReadyState(String name)
	{
		if (this.initArr.containsKey(name))
		{
			this.initArr.put(name, true);
			return true;
		}
		return false;
	}
	
	private boolean areYouReadyKids()
	{	
		for (String strKey : this.initArr.keySet()) {
			if (this.initArr.get(strKey) == false)
			{
				return false;
			}
		}
		return true;
		
	}
	@Override
	protected void initialize() {
		MessageBusImpl.getInstance().register(this);
		Timer timer = new Timer();
		while(!this.areYouReadyKids())
		{
			try {
				TimeUnit.MICROSECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(this.amountOfTicks > this.currentTick) 
		{
			// We should set a timer that counts back ticks on the amount of time delegated by the input.
			timer.schedule(new TimerTask() {				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					TickBroadcast tB = new TickBroadcast();
					tB.setTick(currentTick);
					System.out.println("current tick is :"+ currentTick);
					currentTick++;
					sendBroadcast(tB);
				}
			}, this.tickLength);
			
			
			
			try {
				TimeUnit.MILLISECONDS.sleep(this.tickLength);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		timer.cancel();
		// Die bitch.
		die ter = new die();
		ter.terminate();
		sendBroadcast(ter);
		this.terminate();
			
	}

}
