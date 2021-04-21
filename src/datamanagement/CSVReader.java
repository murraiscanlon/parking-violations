package datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.Ticket;
import logging.Logger;

public class CSVReader {
	
	BufferedReader br;
	String nextLine;
	String fileName;
	
	public CSVReader(String fN) {
		fileName = fN;
	}
	
	public boolean openCSVFile() {
		
		try {
			br = new BufferedReader((new FileReader(fileName)));
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean hasDataLeft() {
		try {
			//if we can read the next line return true
			if ((nextLine = br.readLine()) != null) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	

	public String getNextLine() {
		
		String output = nextLine;
		//clear the next line for the next read
		nextLine = "";
		return output;
		
	}
	
	 public ArrayList<Ticket> loadTicketData(){
	    	//"population.txt";
	    	Logger.getInstance().write(System.currentTimeMillis() + " " + fileName +  "\n");
	        ArrayList<Ticket> ticketData = new ArrayList<>();
	    
	         
	        
	         String line;
	         openCSVFile();
	            
	         while (hasDataLeft()) {
	         	line = getNextLine();
	             String[] values = line.split(",");
	             //2013-04-03T15:15:00Z,36,METER EXPIRED CC,1322731,PA,2905938,19104
	             String date = values[0];
	             long fineDollars = Long.valueOf(values[1]);
	             String descr = values[2];
	             String vehicleID = values[3];
	             String state = values[4];
	             long ticketNumber = Long.valueOf(values[5]);
	             //zipcode may not on every line so check before trying to get the zip code
	             String zipCode;
	             if (values.length == 7) {
	            	 zipCode = values[6];
	             } else {
	            	 zipCode = null;
	             }

	             //TODO not sure if this should happen here or when we print
	             if (state.equals("PA") && zipCode != null) {
	            	 Ticket ticket = new Ticket(ticketNumber,state,date,zipCode,vehicleID,descr, fineDollars);
	            	 ticketData.add(ticket);
	             }
	         }
	           
	         return ticketData;

	       
	    }
	    
	
   
}
