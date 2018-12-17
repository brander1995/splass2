package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class die implements Broadcast{
	
	private boolean terminate=false;
	
	public void terminate()
	{
		this.terminate=true;
	}
	
	
	public boolean getTerminate()
	{
		return this.terminate;
	}
	

}
