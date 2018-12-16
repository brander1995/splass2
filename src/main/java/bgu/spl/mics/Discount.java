package bgu.spl.mics;

public class Discount {
	
	
	private String book;
	private int discount;
	
	
	public Discount(String book, int Discount)
	{
		this.book=book;
		this.discount=Discount;
	}
	
	
	public String getBook()
	{
		return this.book;
	}
	
	
	public int getDiscount()
	{
		return this.discount;
	}
	
	
	public void setDiscount(int newDis)
	{
		this.discount=newDis;
	}

}
