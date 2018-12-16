package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;

public class BookInventoryDeserializer implements JsonDeserializer<BookInventoryInfo> {

	@Override
	public BookInventoryInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
	 	    final JsonObject jsonObject = json.getAsJsonObject();

		    final String booktitle = jsonObject.get("bookTitle").getAsString();
		    final int amountInInventory = jsonObject.get("amount").getAsInt();
		    final int price = jsonObject.get("price").getAsInt();
		    
		    final BookInventoryInfo returnBookInventory = new BookInventoryInfo(booktitle, amountInInventory, price);
		    return returnBookInventory;

	}
	

}
