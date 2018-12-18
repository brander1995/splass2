package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class die implements Broadcast{
	
	@Override
	public String toString() {
		return "die [terminate=" + terminate + "]";
	}


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
