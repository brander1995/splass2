package bgu.spl.mics.application.services;

import java.util.concurrent.ExecutionException;
import bgu.spl.mics.Future;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.JsonObjects.VehicleDeserializer;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
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
				
				DeliveryVehicle vehicle=c.getVehicle();
				vehicle.deliver(c.getAddress(), c.getDistance());
				complete(c, true);
				
			}
			
		};
		
		subscribeEvent(DeliveryEvent.class, delivery);
	}


}
