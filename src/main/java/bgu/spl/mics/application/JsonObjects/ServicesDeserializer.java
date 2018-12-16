package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.services.APIService;
import bgu.spl.mics.application.services.InventoryService;
import bgu.spl.mics.application.services.LogisticsService;
import bgu.spl.mics.application.services.ResourceService;
import bgu.spl.mics.application.services.SellingService;
import bgu.spl.mics.application.services.TimeService;

public class ServicesDeserializer implements JsonDeserializer<MicroService[]>{

	@Override
	public MicroService[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
   	    final JsonObject jsonObject = json.getAsJsonObject();
   	    TimeService tS = context.deserialize(jsonObject.get("time"), TimeService.class);
   	    final int amountOfSellService = jsonObject.get("selling").getAsInt();
   	    final int amountOfInvSercive = jsonObject.get("inventoryService").getAsInt();
   	    final int amountOfLogService = jsonObject.get("logistics").getAsInt();
   	    final int amountOfResourcesServices = jsonObject.get("resourcesService").getAsInt();
   	    APIService[] currServ = context.deserialize(jsonObject.get("customers"), APIService[].class);
		ConcurrentLinkedQueue<MicroService>  myQueue = new ConcurrentLinkedQueue<>();

		
		myQueue.add(tS);
		for (int i = 1; i <= amountOfSellService; i++) {
			myQueue.add(new SellingService(i));
		}
		
		for (int i = 1; i <= amountOfInvSercive; i++) {
			myQueue.add(new InventoryService(i));
		}
		
		for (int i = 1; i <= amountOfLogService; i++) {
			myQueue.add(new LogisticsService(i));
		}
		for (int i = 1; i <= amountOfResourcesServices; i++) {
			myQueue.add(new ResourceService(i));
		}
		
		for (int j = 0; j < currServ.length; j++) {
			myQueue.add(currServ[j]);
		}
		// TODO Auto-generated method stub
		
		return myQueue.toArray(new MicroService[1]);
	}
  
}
