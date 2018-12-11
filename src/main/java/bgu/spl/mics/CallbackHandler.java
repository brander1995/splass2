package bgu.spl.mics;

public class CallbackHandler <T>{
	
	Callback<T> callback;
	Class<T> e_type;
	
	
	public CallbackHandler(Callback<T> callback, Class<T> type)
	{
		this.callback=callback;
		this.e_type=type;
	}
	
	
	
	public Callback<T> EventToCallback(Event <T> e)
	{
		if (e.equals(e_type))
		{
			return this.callback;
		}
		return null;
	}
	
	public Callback<T> getCallbackRegardless()
	{
		return this.callback;
	}
}
