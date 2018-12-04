package bgu.spl.mics;

public class Link<T> {
	
	private T data;
	private Link<T> next;
	
	public Link(T data, Link<T> next)
	{
		this.data=data;
		this.next=next;
	}
	
	public Link<T> getNext()
	{
		return this.next;
	}
	
	public T getData()
	{
		return this.data;
	}
	
	public void setData(T newData)
	{
		this.data=newData;
	}
	
	public void setNext(Link<T> newNext)
	{
		this.next=newNext;
	}
	
}
