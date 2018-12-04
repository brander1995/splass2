package bgu.spl.mics.application.passiveObjects;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class InventoryTest extends TestCase {
	
	private Inventory testInventory;
	private BookInventoryInfo[] inventory;
	
	

	@Before
	public void setup()
	{	
		
		testInventory= Inventory.getInstance();
		
		BookInventoryInfo book1 = new BookInventoryInfo("book1", 5, 50);
		BookInventoryInfo book2 = new BookInventoryInfo("book2", 1, 100);
		BookInventoryInfo book3 = new BookInventoryInfo("book3", 0, 53);
		
		inventory= new BookInventoryInfo[3];
		
		inventory[0]=book1;
		inventory[1]=book2;
		inventory[2]=book3;
		
		testInventory.load(inventory);

	}
	

	@Test
	public void testGetInstance()
	{
		assertNull(testInventory.getInstance());
	}
	
	
	
	
	@Test
	public void testTake()
	{	
		
		OrderResult result;
		int amount;

		
		result=testInventory.take("book1");
		assertEquals(result, OrderResult.SUCCESSFULLY_TAKEN);
		amount =inventory[0].getAmountInInventory();
		assertEquals (amount,4);
		result=testInventory.take("book3");
		assertEquals (result,OrderResult.NOT_IN_STOCK);
		amount=inventory[2].getAmountInInventory();
		assertEquals (amount,0);
		
		
	}
	
	
	@Test
	public void testCheckAvailabiltyAndGetPrice()
	{
		
		int result;
		result= testInventory.checkAvailabiltyAndGetPrice("book1");
		assertEquals(result,50);
		result=testInventory.checkAvailabiltyAndGetPrice("book3");
		assertEquals(result,-1);
		
		
	}
	

}





