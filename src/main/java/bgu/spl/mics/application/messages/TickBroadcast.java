package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast{
	
	private int tick;

	public TickBroadcast()
	{
		this.tick=0;
	}
	
	
	public void setTick(int Tick)
	{
		this.tick=Tick;
	}
	
	public int currentTick()
	{
		return this.tick;
	}

}
