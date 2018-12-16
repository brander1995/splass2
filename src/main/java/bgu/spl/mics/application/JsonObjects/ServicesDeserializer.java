package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;
import java.util.Queue;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.services.APIService;
import bgu.spl.mics.application.services.TimeService;

public class ServicesDeserializer implements JsonDeserializer<MicroService[]>{

	@Override
	public MicroService[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
   	    final JsonObject jsonObject = json.getAsJsonObject();
   	    TimeService tS = context.deserialize(jsonObject.get("time"), TimeService.class);
   	    final int amountOfSellService = jsonObject.get("selling").getAsInt();
   	    final int amountOfInvSercive = jsonObject.get("inventoryService").getAsInt();
   	    final int amountOfLogService = jsonObject.get("logistcs").getAsInt();
   	    final int amountOfResourcesServices = jsonObject.get("resourcesService").getAsInt();
   	    APIService[] currServ = context.deserialize(jsonObject.get("customers"), APIService[].class);
   	    
		// TODO Auto-generated method stub
		return null;
	}

}
