package bgu.spl.mics.application.passiveObjects;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Passive object representing the store finance management. 
 * It should hold a list of receipts issued by the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class MoneyRegister {
	private static class MoneyRegisterHolder{
		private static MoneyRegister instance = new MoneyRegister();
	}
	
	private ConcurrentLinkedQueue<OrderReceipt> register;
	
	
	private MoneyRegister() 
	{
		register = new ConcurrentLinkedQueue<>(); 
	}
	
	
	
	/**
     * Retrieves the single instance of this class.
     */
	
	public static MoneyRegister getInstance() {
		return MoneyRegisterHolder.instance;
	}
	
	/**
     * Saves an order receipt in the money register.
     * <p>   
     * @param r		The receipt to save in the money register.
     */
	public void file (OrderReceipt r) {
		register.add(r);
		}
	
	/**
     * Retrieves the current total earnings of the store.  
     */
	
	
	//TODO : synchronized this? the Queue alone is fine?
	public int getTotalEarnings() {
		
		int totalEarning=0;
		for (OrderReceipt receipt: this.register)
		{
			totalEarning = totalEarning + receipt.getPrice();
		}
		
		return totalEarning;
	}
	
	/**
     * Charges the credit card of the customer a certain amount of money.
     * <p>
     * @param amount 	amount to charge
     */
	
	
	public void chargeCreditCard(Customer c, int amount) {
		
		//TODO what should i do if the customer don't have enough money?
		c.ChargeCustomer(amount);
		
	}
	
	/**
     * Prints to a file named @filename a serialized object List<OrderReceipt> which holds all the order receipts 
     * currently in the MoneyRegister
     * This method is called by the main method in order to generate the output.. 
     */
	public void printOrderReceipts(String filename) {
		//TODO: Implement this
	}
}
