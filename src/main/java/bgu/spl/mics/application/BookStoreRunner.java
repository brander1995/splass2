package bgu.spl.mics.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import bgu.spl.mics.application.JsonObjects.BookInfoInvnetoryDeserializer;
import bgu.spl.mics.application.JsonObjects.InputFileDeserializer;
import bgu.spl.mics.application.JsonObjects.ResourcesHolderDeserializer;
import bgu.spl.mics.application.JsonObjects.VehicleDeserializer;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.InputFile;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
    	
    	if (args.length != 5)
    	{
    		// Fuck this shit.
    		return;
    	}
    	// Serialize the starting input i guess
  
    	// Do i need to unite them under one object?
    	// Lets try.
		InputStream is = null;
		try {
			is = new FileInputStream("./" + args[0]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} /* whatever */
		Reader r = null;
		try {
			r = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    final GsonBuilder gsonBuilder = new GsonBuilder();
		JsonStreamParser p = new JsonStreamParser(r);
	    gsonBuilder.registerTypeAdapter(BookInventoryInfo.class, new BookInfoInvnetoryDeserializer());
		gsonBuilder.registerTypeAdapter(ResourcesHolder.class, new ResourcesHolderDeserializer());
		gsonBuilder.registerTypeAdapter(DeliveryVehicle.class, new VehicleDeserializer());
		gsonBuilder.registerTypeAdapter(InputFile.class, new InputFileDeserializer());
	    final Gson gson = gsonBuilder.create();

		while (p.hasNext()) {
	       JsonElement e = p.next();
	       if (e.isJsonObject()) {
	   		   InputFile testFile = gson.fromJson(e, InputFile.class);
	    	 //  Map m = gson.fromJson(e, Map.class);
	           System.out.println("HEllO");
	           /* do something useful with JSON object .. */
		  }
		  /* handle other JSON data structures */
    		       
    	
    	// Deserizlize the output files.
		}
		

				
    }
}
    
