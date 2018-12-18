

package bgu.spl.mics.application.passiveObjects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive data-object representing a customer of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Customer implements Serializable{
	
	private String name;
	private int id;
	private String address;
	private CreditCard card;
	private ConcurrentLinkedQueue<OrderReceipt> totalReceipt;
	
	private int distance;

	
	public Customer(String Name, int Id, String Address, CreditCard cCard, int dis)
	{
		this.name=Name;
		this.id=Id;
		this.address=Address;
		this.card = cCard;
		this.totalReceipt= new ConcurrentLinkedQueue<>();
		this.distance=dis;
	}
	
	public Customer(String Name, int Id, String Address, int CreditNumber, int money, int dis)
	{
		this.name=Name;
		this.id=Id;
		this.address=Address;
		this.card=new CreditCard(CreditNumber, money);
		this.totalReceipt= new ConcurrentLinkedQueue<>();
		this.distance=dis;
	}
	
	/**
     * Retrieves the name of the customer.
     */
	public String getName() {
		return this.name;
	}

	/**
     * Retrieves the ID of the customer  . 
     */
	public int getId() {
		return this.id;
	}
	
	/**
     * Retrieves the address of the customer.  
     */
	public String getAddress() {
		return this.address;
	}
	
	/**
     * Retrieves the distance of the customer from the store.  
     */
	public int getDistance() {
		return this.distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     * @return A list of receipts.
     */
	public List<OrderReceipt> getCustomerReceiptList() {
		return (new LinkedList<OrderReceipt>(this.totalReceipt));
	}
	
	/**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     * @return Amount of money left.   
     */
	public int getAvailableCreditAmount() {
		return this.card.getMoney();
	}
	
	/**
     * Retrieves this customers credit card serial number.    
     */
	public int getCreditNumber() {
		return this.card.getCreditNumber();
	}

	
	
	public boolean ChargeCustomer(int amount)
	{
		return this.card.ChargeCred(amount);
	}
	
	
	public void addReceipt(OrderReceipt r)
	{
		if (r != null) {
			totalReceipt.add(r);
		}
	}
}
