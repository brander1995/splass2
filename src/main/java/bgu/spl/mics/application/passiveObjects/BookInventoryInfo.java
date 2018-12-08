// implemented by Rachel 26/11
//TODO Rachel: synchronize if needed 


package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a information about a certain book in the inventory.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class BookInventoryInfo {

	private String booktitle;
	private int amountInInventory;
	private int price;
	
	
	private Object lockAmountInInventory;// in order to synchronized increase and decrease  without locking the entire class
	
	
	
	
	
	/**
     * Retrieves the title of this book.
     * <p>
     * @return The title of this book.   
     */
	
	public BookInventoryInfo(String title,int amount, int bookPrice)
	{
		this.booktitle=title;
		this.amountInInventory=amount;
		this.price=bookPrice;
	}
	
	public String getBookTitle() {
		
		return this.booktitle;
	}

	/**
     * Retrieves the amount of books of this type in the inventory.
     * <p>
     * @return amount of available books.      
     */
	public int getAmountInInventory() {
		
		synchronized (this.lockAmountInInventory) {
			return this.amountInInventory;
		}
	}

	/**
     * Retrieves the price for  book.
     * <p>
     * @return the price of the book.
     */
	public int getPrice() {
		

		return this.price;
	}
	
	public void increaseAmountInInventory()
	{
		synchronized(this.lockAmountInInventory)
		{
			this.amountInInventory++;
		}
	}
	
	
	//decreases the AmountInInventory if possible. return true is succeeded and false otherwise.

	public boolean decreaseAmountInInventory()
	{
		synchronized (this.lockAmountInInventory) 
		{
			if (this.amountInInventory>0)
			{
				this.amountInInventory--;
				return true;
			}
			return false;
				
		}
	}

	
}
