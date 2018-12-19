package bgu.spl.mics;


import java.util.concurrent.TimeUnit;

import bgu.spl.mics.application.services.TimeService;


/**
 * A Future object represents a promised result - an object that will
 * eventually be resolved to hold a result of some operation. The class allows
 * Retrieving the result once it is available.
 * 
 * Only private methods may be added to this class.
 * No public constructor is allowed except for the empty constructor.
 */
public class Future<T> {
	
	private T result;
	private boolean hasChanged = false;
	
	
	/**
	 * This should be the the only public constructor in this class.
	 */
	
	
	public Future() {
		
		this.result=null;
		this.hasChanged = false;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved.
     * This is a blocking method! It waits for the computation in case it has
     * not been completed.
     * <p>
     * @return return the result of type T if it is available, if not wait until it is available.
     * 	       
     */
	
	
	public T get() {
		
		//waits until resolve is done
		this.returnWhenResolveIsFinished();
		return this.result;

	}
	
	/**
     * Resolves the result of this Future object.
     */
	
	
	public synchronized void resolve (T result) {
		
		this.result=result;
		this.hasChanged=true;
		this.notifyAll();
	}
	
	
	//Wait & Notify 
	private synchronized void returnWhenResolveIsFinished()
	{
		while (!hasChanged)
		{
			try {
				this.wait();
			}
				catch (InterruptedException e) {}
			
		}
	}
	
	/**
     * @return true if this object has been resolved, false otherwise
     */
	
	
	//TODO rachel: synchronized this?
	public boolean isDone() {
		if (hasChanged)
			return true;
		
		return false;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved,
     * This method is non-blocking, it has a limited amount of time determined
     * by {@code timeout}
     * <p>
     * @param timout 	the maximal amount of time units to wait for the result.
     * @param unit		the {@link TimeUnit} time units to wait.
     * @return return the result of type T if it is available, if not, 
     * 	       wait for {@code timeout} TimeUnits {@code unit}. If time has
     *         elapsed, return null.
     */
	
	
	public  T get(long timeout, TimeUnit unit) {
		
		int divisionNumber = TimeService.getInstance().amountOfTicksLeft() * 4;
		if (this.isDone())
		{
			return result;
		}
		else
		{
			// Does not include clock skew, but its no missile so im fine with this.
			for (int i = 0;
				 (i < divisionNumber) && (!this.isDone());
				 i++)
			{
				try {
					unit.sleep(timeout/(divisionNumber + 5));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (this.isDone())
					return result;
			}
		}
		return null;
	}
	

}
