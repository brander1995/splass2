package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.passiveObjects.CreditCard;

public class CreditCardDeserializer implements JsonDeserializer<CreditCard> {

	@Override
	public CreditCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		final int number = jsonObject.get("number").getAsInt();
		final int amount = jsonObject.get("amount").getAsInt();
		final CreditCard rCredit = new CreditCard(number, amount);
		return rCredit;
	}

}
