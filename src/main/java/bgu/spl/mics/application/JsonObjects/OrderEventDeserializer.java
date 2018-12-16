package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.messages.CustomerOrderEvent;

public class OrderEventDeserializer implements JsonDeserializer<CustomerOrderEvent>{

	@Override
	public CustomerOrderEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		final String bookName = jsonObject.get("bookTitle").getAsString();
		final int tick = jsonObject.get("tick").getAsInt();
		CustomerOrderEvent cevent = new CustomerOrderEvent(bookName, tick);
		
		// TODO Auto-generated method stub
		return cevent;
	}

}
