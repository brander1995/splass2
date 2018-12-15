package bgu.spl.mics.application.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeliveryEvent;
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

	ResourcesHolder resource;
	
	
	public ResourceService() {
		super("Resource Service");
		this.resource=ResourcesHolder.getInstance();
	}

	@Override
	protected void initialize() {
		
		Callback<resourceEvent> r= new Callback<resourceEvent>() {
			
			@Override
			public void call(resourceEvent c) {
				
				Future<DeliveryVehicle> v= (Future<DeliveryVehicle>) resource.acquireVehicle();
				DeliveryVehicle vehicle=null;
				try {
					//gets a vehicle free for delivery
					vehicle = v.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (vehicle!=null)
				{
					
				//sends delivery Event with vehicle
				DeliveryEvent delivery= new DeliveryEvent(c.getAddress(), c.getDistance(), "Resource Service", vehicle);
				Future<Boolean> result= (Future<Boolean>) sendEvent(delivery);
				try {
					result.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//releases the vehicle when the delivery ends
				resource.releaseVehicle(vehicle);
				
				}
				
			}
		};
		// TODO finish try and catch

		
		subscribeEvent(resourceEvent.class, r);
		
	}

}
