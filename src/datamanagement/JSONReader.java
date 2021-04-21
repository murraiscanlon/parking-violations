
package datamanagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import data.Ticket;
public class JSONReader {
	
	private String fileName;
	
	public JSONReader(String fN) {
		fileName = fN;
	}
	
	 public ArrayList<Ticket> loadTicketData(){
	    	JSONParser parser = new JSONParser();			
			// open the file and get the array of JSON objects
			JSONArray parkingArrayJSON;
			ArrayList<Ticket> ticketData = null; 
			
			
			try {
				parkingArrayJSON = (JSONArray)parser.parse(new FileReader(fileName));
				ticketData = new ArrayList<>();
				for(int i = 0 ; i < parkingArrayJSON.size(); i++) {
					JSONObject curObj = (JSONObject) parkingArrayJSON.get(i);

					Ticket newTicket = new Ticket(curObj);
					ticketData.add(newTicket);
					
				}
				
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return ticketData;
	    }
	
  
    }



