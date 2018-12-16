package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

public class VehicleDeserializer implements JsonDeserializer<DeliveryVehicle> {

	@Override
	public DeliveryVehicle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
			final JsonObject jsonObects = json.getAsJsonObject();
			
			final DeliveryVehicle rVehicle = new DeliveryVehicle(jsonObects.get("license").getAsInt(), jsonObects.get("speed").getAsInt());
			return rVehicle;
	}

}
