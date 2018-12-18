package bgu.spl.mics.application.passiveObjects;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Passive data-object representing the store inventory.
 * It holds a collection of {@link BookInventoryInfo} for all the
 * books in the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {
	private static class SingletonInventoryHolder{
		private static Inventory instance= new Inventory();
	}
	
	private ConcurrentLinkedQueue<BookInventoryInfo> inventoryList;
	
	private Inventory() {
		this.inventoryList= new ConcurrentLinkedQueue<BookInventoryInfo>();
	}
	
	
	/**
     * Retrieves the single instance of this class.
     */
	public static Inventory getInstance() {
		return SingletonInventoryHolder.instance;
	}
	
	/**
     * Initializes the store inventory. This method adds all the items given to the store
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load (BookInventoryInfo[ ] inventory ) {
		
		//we don't need to synchronized this. the implementation of AtomicLinkedList is thread safe
		for (BookInventoryInfo book: inventory)
		{
			this.inventoryList.add(book);
		}
		
		
	}
	
	/**
     * Attempts to take one book from the store.
     * <p>
     * @param book 		Name of the book to take from the store
     * @return 	an {@link Enum} with options NOT_IN_STOCK and SUCCESSFULLY_TAKEN.
     * 			The first should not change the state of the inventory while the 
     * 			second should reduce by one the number of books of the desired type.
     */
	
	
	
	public OrderResult take (String book) {
		
		boolean taken=false;
		for (BookInventoryInfo Book:inventoryList)
		{
			if (Book.getBookTitle().equals(book))
			{
				//decreaseAmountInInvemtory is thread safe
				taken=Book.decreaseAmountInInventory();

			}
			
		}
		if (taken)
			return OrderResult.SUCCESSFULLY_TAKEN;
		else
			return OrderResult.NOT_IN_STOCK;
	}
	
	
	
	/**
     * Checks if a certain book is available in the inventory.
     * <p>
     * @param book 		Name of the book.
     * @return the price of the book if it is available, -1 otherwise.
     */
	public int checkAvailabiltyAndGetPrice(String book) {
		
		for (BookInventoryInfo Book:inventoryList)
		{
			if (Book.getBookTitle().equals(book)&&Book.getAmountInInventory()>0)
				return Book.getPrice();//TODO :  safe? getPrice is not synchronized.
				
		}
		return -1;
	}
	
	/**
     * 
     * <p>
     * Prints to a file name @filename a serialized object HashMap<String,Integer> which is a Map of all the books in the inventory. The keys of the Map (type {@link String})
     * should be the titles of the books while the values (type {@link Integer}) should be
     * their respective available amount in the inventory. 
     * This method is called by the main method in order to generate the output.
     */
	public void printInventoryToFile(String filename){
		HashMap<String, Integer> tempMap = new HashMap<>();
		
		for (BookInventoryInfo cBook : this.inventoryList)
		{
			tempMap.put(cBook.getBookTitle(), cBook.getAmountInInventory());
		}
		
		FileOutputStream out = null;
		
		try {
			out = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GsonBuilder builder = new GsonBuilder();
        Gson gson =
            builder.enableComplexMapKeySerialization().setPrettyPrinting().create();
        
		try {
			out.write(gson.toJson(tempMap).getBytes());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
}
