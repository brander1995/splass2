package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.messages.CustomerOrderEvent;
import bgu.spl.mics.application.passiveObjects.CreditCard;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.services.APIService;

public class APIServiceDeserializer implements JsonDeserializer<APIService> {

	@Override
	public APIService deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		
		final String strName = jsonObject.get("name").getAsString();
		final long idCust = jsonObject.get("id").getAsLong();
		final String strAddr = jsonObject.get("address").getAsString();
		final CreditCard creditForCust = context.deserialize(jsonObject.get("creditCard"), CreditCard.class);
		
		final Customer 	currCust = new Customer(strName, Math.toIntExact(idCust), strAddr, creditForCust);
		
		final CustomerOrderEvent[] orderEvents = context.deserialize(jsonObject.get("orderSchedule"), CustomerOrderEvent[].class);
		
		final APIService rService = new APIService( currCust,
													// Wanna know why we can't have nice things? this is why we can't have nice things.
													new ConcurrentLinkedQueue<CustomerOrderEvent>(Arrays.asList(orderEvents)));
		
		// TODO Auto-generated method stub1
		return rService;
	}

}
