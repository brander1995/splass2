package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

public class ResourceService extends MicroService {

	ResourcesHolder resource;
	
	
	public ResourceService() {
		super("Resource Service");
		this.resource=ResourcesHolder.getInstance();
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

}
