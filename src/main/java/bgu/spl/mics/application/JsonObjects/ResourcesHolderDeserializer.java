package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

public class ResourcesHolderDeserializer implements JsonDeserializer<ResourcesHolder> {

	@Override
	public ResourcesHolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// Delegate the deserialization to the context
	    final JsonObject jsonObject = json.getAsJsonObject();
	    
	    DeliveryVehicle[] initVehicles = context.deserialize(jsonObject.get("vehicles"), DeliveryVehicle[].class);
	    ResourcesHolder.getInstance().load(initVehicles);
	    
		return ResourcesHolder.getInstance();
	}

}
