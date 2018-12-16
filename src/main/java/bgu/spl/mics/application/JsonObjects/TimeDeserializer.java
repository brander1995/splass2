package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.services.TimeService;

public class TimeDeserializer implements JsonDeserializer<TimeService> {

	@Override
	public TimeService deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
			final JsonObject jsonObjects = json.getAsJsonObject();
			
			final int initSpeed = jsonObjects.get("speed").getAsInt();
			final int initDuration = jsonObjects.get("duration").getAsInt();
			
			TimeService.getInstance().setAmountOfTicks(initDuration);
			TimeService.getInstance().setTickLength(initSpeed);
			
			
		// TODO Auto-generated method stub
		return TimeService.getInstance();
	}

}
