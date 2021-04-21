package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import datamanagement.CSVReader;
import datamanagement.TXTReader;
import logging.Logger;

public class Ticket {
    long ticketNumber;
    String plateState;
    String date;
    String zipCode;
    String vehicleID;
    String descr;    
    long fineDollars;
    

    public Ticket(long ticketNumber, String plateId, String date, String zip, 
    		String vehicleID,String descr, long fineDollars) {
        this.ticketNumber = ticketNumber;
        this.plateState = plateId;
        this.date = date;
        this.zipCode = zip;
        this.vehicleID = vehicleID;
        this.fineDollars = fineDollars;
        this.descr = descr;

    }
    
    public Ticket(JSONObject obj) {
    	this.date = (String) obj.get("date");
    	this.fineDollars = (long) obj.get("fine");
    	this.descr = (String) obj.get("violation");
    	this.vehicleID= (String) obj.get("plate_id");
    	this.plateState = (String) obj.get("state");
    	this.ticketNumber = (long) obj.get("ticket_number");
    	this.zipCode = (String) obj.get("zip_code");
    	
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public String getPlateState() {
        return plateState;
    }

    public String getDate() {
        return date;
    } 

    public String getZipCode() {
		return zipCode;
	}

	public String getVehicleID() {
		return vehicleID;
	}

	public String getDescr() {
		return descr;
	}

	public long getFine() {
		return fineDollars;
	}

	public void print() {
        System.out.println("Ticket Number: " + ticketNumber + ", "
                + "plate id:" + plateState + ", "
                + "date:" + date + ", "
                + "zip:" + zipCode);
    }

   
   
}
