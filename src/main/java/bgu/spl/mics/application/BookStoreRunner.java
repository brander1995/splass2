package bgu.spl.mics.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import bgu.spl.mics.DebugInfo;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.JsonObjects.APIServiceDeserializer;
import bgu.spl.mics.application.JsonObjects.BookInfoInvnetoryDeserializer;
import bgu.spl.mics.application.JsonObjects.CreditCardDeserializer;
import bgu.spl.mics.application.JsonObjects.InputFileDeserializer;
import bgu.spl.mics.application.JsonObjects.OrderEventDeserializer;
import bgu.spl.mics.application.JsonObjects.ResourcesHolderDeserializer;
import bgu.spl.mics.application.JsonObjects.ServicesDeserializer;
import bgu.spl.mics.application.JsonObjects.TimeDeserializer;
import bgu.spl.mics.application.JsonObjects.VehicleDeserializer;
import bgu.spl.mics.application.messages.CustomerOrderEvent;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.CreditCard;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import bgu.spl.mics.application.passiveObjects.InputFile;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import bgu.spl.mics.application.services.APIService;
import bgu.spl.mics.application.services.TimeService;

/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
    	
    	// Serialize the starting input i guess
  
    	// Do i need to unite them under one object?
    	// Lets try.
		InputStream is = null;
		try {
			is = new FileInputStream(args[0]);
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
		
		// Generate the gson bulder that will read the input json
	    final GsonBuilder gsonBuilder = new GsonBuilder();
		JsonStreamParser p = new JsonStreamParser(r);
	    gsonBuilder.registerTypeAdapter(BookInventoryInfo.class, new BookInfoInvnetoryDeserializer());
		gsonBuilder.registerTypeAdapter(ResourcesHolder.class, new ResourcesHolderDeserializer());
		gsonBuilder.registerTypeAdapter(DeliveryVehicle.class, new VehicleDeserializer());
		gsonBuilder.registerTypeAdapter(InputFile.class, new InputFileDeserializer());
		gsonBuilder.registerTypeAdapter(MicroService[].class, new ServicesDeserializer());
		gsonBuilder.registerTypeAdapter(TimeService.class, new TimeDeserializer());
		gsonBuilder.registerTypeAdapter(APIService.class, new APIServiceDeserializer());
		gsonBuilder.registerTypeAdapter(CustomerOrderEvent.class, new OrderEventDeserializer());
		gsonBuilder.registerTypeAdapter(CreditCard.class, new CreditCardDeserializer());
		
		
		// Serializers
		
	    final Gson gson = gsonBuilder.setPrettyPrinting().create();
	    InputFile testFile = null;
		while (p.hasNext()) {
	       JsonElement e = p.next();
	       if (e.isJsonObject()) {
	   		   testFile = gson.fromJson(e, InputFile.class);
		  }
	     LinkedList<Thread> threadList = new LinkedList<>();
	     if (testFile == null)
	     {
	    	 break;
	     }
	     
	     TimeService.getInstance().generateInitArray(testFile.initialServices);
	     
	     for (MicroService mService : testFile.initialServices) {
			Thread currThread = new Thread(mService);
			threadList.add(currThread);
			DebugInfo.PrintDebug("starting thread " + mService.getName());
			currThread.start();
		}  
	     
		  /* handle other JSON data structures */
    	 
	      for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
    	
	      // Deserizlize the output files.
	     
	      // Customer Map
	      HashMap<Integer, Customer> mCustMap = new HashMap<>();
	      
	      
	      for (MicroService mServ : testFile.initialServices)
	      {
	    	  if (mServ instanceof APIService)
	    	  {
	    		  mCustMap.put(((APIService)mServ).getCustomerConnected().getId(), ((APIService)mServ).getCustomerConnected());
	    	  }
	      }
		
	      
	      
	      FileOutputStream out = null;
	      ObjectOutputStream oos= null;
	      
	      
	      try {
	    	out = new FileOutputStream(args[1]);
			oos = new ObjectOutputStream(out);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	      
	      
	      try {
			oos.writeObject(mCustMap);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	      
	      
	      finally {
			if (out!=null)
			{
				try {
					out.close();
				}
			catch (IOException e2) {
				e2.printStackTrace();
			}
			}
			if (oos!=null)
			{
				try {
					oos.close();
				}catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		}
	      
	    Inventory.getInstance().printInventoryToFile(args[2]);
   	  	MoneyRegister.getInstance().printOrderReceipts(args[3]); 
	 
	     
	     


	      try {
	    	out = new FileOutputStream(args[4]);
			oos = new ObjectOutputStream(out);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	      
	      
	      try {
			oos.writeObject(MoneyRegister.getInstance());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	      
	      
	      finally {
			if (out!=null)
			{
				try {
					out.close();
				}
			catch (IOException e2) {
				e2.printStackTrace();
			}
			}
			if (oos!=null)
			{
				try {
					oos.close();
				}catch (IOException e3) {
					e3.printStackTrace();
				}
			}
	      }
		}
		

				
    }
}
    
