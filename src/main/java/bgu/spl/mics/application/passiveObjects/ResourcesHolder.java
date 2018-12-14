package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.Future;

/**
 * Passive object representing the resource manager.
 * You must not alter any of the given public methods of this class.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class ResourcesHolder {
	private static class SingletonResourcesHolder{
		private static ResourcesHolder instance= new ResourcesHolder();
	}
	
	private ConcurrentLinkedQueue<DeliveryVehicle> AvailableVehiclesList;
	private ConcurrentLinkedQueue<DeliveryVehicle> occupiedVehiclesList;
	
	
	private ResourcesHolder()
	{
		this.AvailableVehiclesList= new ConcurrentLinkedQueue<>();
		this.occupiedVehiclesList= new ConcurrentLinkedQueue<>();
	}
	
	
	/**
     * Retrieves the single instance of this class.
     */
	
	
	public static ResourcesHolder getInstance() {
		return SingletonResourcesHolder.instance;
	}
	
	/**
     * Tries to acquire a vehicle and gives a future object which will
     * resolve to a vehicle.
     * <p>
     * @return 	{@link Future<DeliveryVehicle>} object which will resolve to a 
     * 			{@link DeliveryVehicle} when completed.   
     */
	public Future<DeliveryVehicle> acquireVehicle() {
		//TODO: Implement this
		return null;
	}
	
	/**
     * Releases a specified vehicle, opening it again for the possibility of
     * acquisition.
     * <p>
     * @param vehicle	{@link DeliveryVehicle} to be released.
     */
	
	
	//TODO: should i synch this? what happens if acquireVehicle and releaseVehicle are called at the same time?
	public void releaseVehicle(DeliveryVehicle vehicle) {
		
		for (DeliveryVehicle v:occupiedVehiclesList)
		{
			if (v.equals(vehicle))
			{
				occupiedVehiclesList.remove(v);
				AvailableVehiclesList.add(v);
			}
		}
		
	}
	
	/**
     * Receives a collection of vehicles and stores them.
     * <p>
     * @param vehicles	Array of {@link DeliveryVehicle} instances to store.
     */
	
	
	//TODO: should i synch this?
	public void load(DeliveryVehicle[] vehicles) {
		
		
		for (DeliveryVehicle vehicle: vehicles)
		{
			this.AvailableVehiclesList.add(vehicle);
		}
	}

}
