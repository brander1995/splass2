package bgu.spl.mics.application.JsonObjects;

import java.io.FileFilter;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.InputFile;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

public class InputFileDeserializer implements JsonDeserializer<InputFile> {

	@Override
	public InputFile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		final JsonObject jsonObject = json.getAsJsonObject();
		final InputFile filyFile = new InputFile();
		BookInventoryInfo[] myBooks  = context.deserialize(jsonObject.get("initialInventory"), BookInventoryInfo[].class);
		filyFile.intialInventory = Inventory.getInstance();
		filyFile.intialInventory.load(myBooks);
		
		ResourcesHolder[] testyTest = context.deserialize(jsonObject.get("initialResources"), ResourcesHolder[].class);
		filyFile.IntialResources = ResourcesHolder.getInstance();
		filyFile.IntialResources = testyTest[0];
		
		MicroService[] testyTest2 = context.deserialize(jsonObject.get("services") , MicroService[].class);
		filyFile.initialServices =  testyTest2;

		return filyFile;
	}

}
