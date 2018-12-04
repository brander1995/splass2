package bgu.spl.mics;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import junit.framework.TestCase;

public class FutureTest extends TestCase {
	
	
	private Future<String> testFuture= new Future<>();
	
	@Test
	public void testGet()
	{
		assertEquals(testFuture.get(),null);
		testFuture.resolve("result");
		assertEquals(testFuture.get(), "result");
	}
	
	@Test
	public void testGetTwice()
	{
		testFuture.resolve("result");
		assertEquals(testFuture.get(), "result");
		testFuture.resolve("new result");
		assertEquals(testFuture.get(), "new result");
	}
	
	/*
	 * but testing resolve and get must come together.
	 * 
	 * */
	@Test
	public void testResolve()
	{
		testFuture.resolve("result");
		assertEquals(testFuture.get(), "result");
	}
	
	@Test
	public void testIsDone()
	{
		assertFalse(testFuture.isDone());
		testFuture.resolve("result");
		assertTrue(testFuture.isDone());

	}
	
	
	@Test(timeout=10000)
	public void testGetWithTime1()
	{
		assertNotSame(testFuture.get(1111, TimeUnit.MILLISECONDS), "testing");		
	}
	
	@Test(timeout=10000)
	public void testGetWithTime2()
	{
		testFuture.resolve("result");
		assertEquals(testFuture.get(), "result");
	}
	
	

	
	
	
	
	
	
	
}

