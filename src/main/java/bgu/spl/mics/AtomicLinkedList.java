package bgu.spl.mics;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicLinkedList<T> {
	
	private AtomicReference<Link<T>> head = new AtomicReference<Link<T>>();

	private Object key;
	
	public AtomicLinkedList()
	{
		
	}
	
	public void add (T data)
	{
		Link<T> localHead;
		Link<T> newHead = new Link<>(data ,null);
		
		do
		{
			localHead=head.get();
			newHead.setNext(localHead);
			
		}while (!head.compareAndSet(localHead, newHead));
		

	}
	
	
	public T  remove(T toRemove)
	{
		Link<T> removeLink=head.get();
		
		synchronized (key)
		{
			while (removeLink.getData()!=toRemove)
			{
					
				removeLink=removeLink.getNext();
			}
		}
		
		
		return null;
	}
	

}
