package bgu.spl.mics.application.JsonObjects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import bgu.spl.mics.application.passiveObjects.InputFile;

public class InputFileDeserializer implements JsonDeserializer<InputFile> {

	@Override
	public InputFile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
