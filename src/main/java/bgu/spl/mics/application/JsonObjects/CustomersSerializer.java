package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import bgu.spl.mics.application.passiveObjects.Customer;

public class CustomersSerializer implements JsonSerializer<HashMap<Integer, Customer>> {
	@Override
	public JsonElement serialize(HashMap<Integer, Customer> src, Type typeOfSrc, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		for(Integer key : src.keySet())
		{
			jsonObject.addProperty(key.toString(), src.get(key).toString());	
		}
		
		return jsonObject;
	}
	
}
