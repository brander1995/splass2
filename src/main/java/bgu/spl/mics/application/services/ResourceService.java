package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;

import java.util.concurrent.TimeUnit;

import bgu.spl.mics.Callback;
import bgu.spl.mics.DebugInfo;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.die;
import bgu.spl.mics.application.messages.resourceEvent;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

/**
 * ResourceService is in charge of the store resources - the delivery vehicles.
 * Holds a reference to the {@link ResourceHolder} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ResourceService extends MicroService{

	private ResourcesHolder resource;
	int Curtick=-1;
	String name;
	
	
	public ResourceService(Integer nameNum) {
		super("Resource Service"+nameNum.toString());
		this.resource=ResourcesHolder.getInstance();
		this.name="Resource Service"+nameNum.toString();
	}

	@Override
	protected void initialize() {
		
		
		MessageBusImpl.getInstance().register(this);
		this.subscribeResourceEvent();
		this.SubscribeTimeBroadcast();
		this.subscribeDieBroadcast();
		
		TimeService.getInstance().setReadyState(this.getName());
	}
	
	private void subscribeResourceEvent()
	{
		Callback<resourceEvent> r= new Callback<resourceEvent>() {
			
			@Override
			public void call(resourceEvent c) {
				
				Future<DeliveryVehicle> vehicleFuture= (Future<DeliveryVehicle>) resource.acquireVehicle();
				DebugInfo.PrintHandle(getName() + " using get on deliveryVehicle ");
				DeliveryVehicle vehicle = vehicleFuture.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS);;
	
				//sends delivery Event with vehicle
				DeliveryEvent delivery= new DeliveryEvent(c.getAddress(), c.getDistance(), name, vehicle);
				Future<Boolean> result= (Future<Boolean>) sendEvent(delivery);
				DebugInfo.PrintHandle(getName() + " using get on get deliveryEvent ");
				result.get(TimeService.getInstance().amountLeftInMS(), TimeUnit.MILLISECONDS);;

				//releases the vehicle when the delivery ends
				resource.releaseVehicle(vehicle);
				
				complete(c, true);
				}
		};
		// TODO finish try and catch

		
		subscribeEvent(resourceEvent.class, r);
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
