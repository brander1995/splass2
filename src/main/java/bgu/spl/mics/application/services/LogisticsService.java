package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

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

	public LogisticsService() {
		super("Logistics Service");
	}

	@Override
	protected void initialize() {
		
		MessageBusImpl.getInstance().register(this);
		
		this.SubscribeDeliveryEvent();
		
	
		

		
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
