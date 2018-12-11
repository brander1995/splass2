package bgu.spl.mics;

public class EventToFuture {
	
	private Future<?> future;
	private Event<?> event;
	
	public EventToFuture(Future<?> future, Event<?> event)
	
	{
		this.future=future;
		this.event=event;
	}

	
	
	public Future<?> getFuture()
	{
		return this.future;
	}
	
	
	public Event<?> getEvent()
	{
		return this.event;
	}
	
	public Future<?> getReleventFuture(Event<?> e){
		
		if ((e.getClass() == this.event.getClass()) && (e == this.event))
		{
			return this.future;
		}
		return null;
	}
	
	public Event<?> getReleventEvent( Future <?> f)
	{
		if (f==this.future)
		{
			return this.event;
		}
		return null;
	}
}


