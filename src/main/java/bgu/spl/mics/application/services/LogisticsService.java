package bgu.spl.mics.application.services;

import java.util.concurrent.ExecutionException;
import bgu.spl.mics.Future;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.JsonObjects.VehicleDeserializer;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.die;
import bgu.spl.mics.application.messages.resourceEvent;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

/**
 * Logistic service in charge of delivering books that have been purchased to customers.
 * Handles {@link DeliveryEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LogisticsService extends MicroService {

	int Curtick=-1;
	String name;
	
	public LogisticsService(Integer nameNum) {
		super("Logistics Service"+nameNum.toString());
		this.name="Logistics Service"+nameNum.toString();
	}

	@Override
	protected void initialize() {
		
		MessageBusImpl.getInstance().register(this);
		
		this.SubscribeDeliveryEvent();
		
		this.SubscribeTimeBroadcast();
		
		this.subscribeDieBroadcast();

		TimeService.getInstance().setReadyState(this.getName());
	}
	
	
	private void SubscribeTimeBroadcast()
	{
		Callback<TickBroadcast> tick= new Callback<TickBroadcast>() {

			@Override
			public void call(TickBroadcast c) {
				Curtick=c.currentTick();
				
			}
			
			
		};
		
		super.subscribeBroadcast(TickBroadcast.class, tick);
	}
	
	
	
	private void SubscribeDeliveryEvent()
	{
		Callback<DeliveryEvent> delivery= new Callback<DeliveryEvent>() {

			@Override
			public void call(DeliveryEvent c) {
//				// https://www.cs.bgu.ac.il/~spl191/phpBB/viewtopic.php?f=4&t=362&p=891&hilit=acquireVehicle&sid=4ddea46d831eaf135c591057e0433ff3#p891
//				So we need to move the delivery here from the resourceHolder.
//				We try and create a new delivery, and aquire a vehicle from the resource holder,
//				resolve the future to get the vehicle, and then do the delivery
				
				
				DeliveryVehicle vehicle=c.getVehicle();
				vehicle.deliver(c.getAddress(), c.getDistance());
				complete(c, true);
				
			}
			
		};
		
		subscribeEvent(DeliveryEvent.class, delivery);
	}
	
	
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
