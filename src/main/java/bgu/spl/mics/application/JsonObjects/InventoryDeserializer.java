package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

// Not needed
public class InventoryDeserializer implements JsonDeserializer<Inventory> {

	@Override
	public Inventory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
//	 	    final JsonObject jsonObject = json.getAsJsonObject();
//	 	    BookInventoryInfo[] initVehicles = context.deserialize(jsonObject.get("vehicles"), BookInventoryInfo[].class);
//		    ResourcesHolder.getInstance().load(initVehicles);
//		    
//			return ResourcesHolder.getInstance();
		

		return null;
	}
	

}
