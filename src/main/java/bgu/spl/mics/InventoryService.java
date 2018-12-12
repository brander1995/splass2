package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;

public class InventoryService extends MicroService{

	Inventory inventory;
	
	
	public InventoryService() {
		super("Invemtory Service");
		this.inventory=Inventory.getInstance();
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
