package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.atomic.AtomicInteger;

public class CreditCard {
	
	private int number;
	private AtomicInteger money;
	
	public CreditCard(int number, int money)
	{
		this.money=new AtomicInteger(money);
		this.number=number;
	}
	

	public boolean ChargeCred(int amount)
	{
		if (this.money.get() < amount)
			return false;
		int val;
		do
		{
			val=money.get();
			
		}while (!money.compareAndSet(val, val-amount));
		
		return true;
	
	}
	
	public int getCreditNumber()
	{
		return this.number;
	}
	
	
	public int getMoney()
	{
		return this.money.get();
	}
	
	
	
	

}
