package processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import logging.Logger;

import data.Home;
import data.Population;
import data.Ticket;
import datamanagement.*;
public class Main {

	/**
	 *
	 * runtime args: programName formatParking (csv or json) nameOfParkingFile nameOfPropertyFile nameOfPopFile nameOfLogFile
	 * @param
	 *
	 */


    public static void main(String[] args) {
    	    	
    	//create the logger
    	Logger.createInstance(args[4]);
    	//print all runtime arguments to the file
    	Logger.getInstance().write(System.currentTimeMillis() + " ");
    	for(String s : args) {
    		Logger.getInstance().write(s + " ");
    	}
    	Logger.getInstance().write("\n");
    	
    	//Memoization
    	HashMap<String, Double> PopulationTickets = new HashMap<>();
    	HashMap<String, Double> RatioViolationsPerMarketValue = new HashMap<>();
    	
    	String formatOfParking = args[0];
    	
    	
    	//get population data
        ArrayList<Population> popData = Population.loadFromTXT(args[3]); //will need to come from runtime args eventually
        //sort the pop data
        Collections.sort(popData);
        ArrayList<Ticket> ticketData = new ArrayList<>();
        if (formatOfParking.toLowerCase().equals("csv")) {
	        //get ticket data
        	CSVReader reader = new CSVReader(args[1]);
	     	ticketData = reader.loadTicketData();
        } else if (formatOfParking.toLowerCase().equals("json")) {
        	JSONReader reader = new JSONReader(args[1]);
        	ticketData = reader.loadTicketData();
        } else {
        	System.out.println("Error: invalid program argument for Parking File Type: " + args[0]);
        }
    	//get home data
     	ArrayList<Home> homeData = Home.loadFromCSV(args[2]);
     	
     	AnalyzeHomeMarketValue mv = new AnalyzeHomeMarketValue();
     
    	
    	//display welcome message and get ready for user input
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("-----------------------------");
    	System.out.println("- Welcome to OpenPhillyData -");
		System.out.println("-----------------------------");

    	
    	while(true) {
    		System.out.println();
    		System.out.println("\t1. Calculate total population of all zip codes ");
    		System.out.println("\t2. Calculate total parking fines per capita for each zip code");
    		System.out.println("\t3. Calculate average market value for a zip code");
    		System.out.println("\t4. Calculate total livable area for a zip code");
    		System.out.println("\t5. Calculate total residential market value per capita for a zip code");
    		System.out.println("\t6. Calculate the ratio of parking violations to avg marget value for each zip code");
    		System.out.println("\t0. EXIT \n");
			System.out.print("Enter the number of your selection here:");

			//get command line argument for txt or json spec
			int userInput;
    		userInput = scanner.nextInt();	
    		//userInput = 4;
    		Logger.getInstance().write( System.currentTimeMillis() + " " + userInput + "\n");
    		if (userInput == 1) {
    			option1(popData);
    		} else if (userInput == 2) {
    			option2(popData,ticketData,PopulationTickets);
    		} else if (userInput == 3) {
    			System.out.println("Please enter the zip code you want the average market value for:");
    	    	String zipCode = scanner.next();    
    	    	Logger.getInstance().write( System.currentTimeMillis() + " " + zipCode + "\n");
    			option3(homeData, zipCode);
    		} else if (userInput == 4) {
    			System.out.println("Please enter the zip code you want the average total livable area for:");
    	    	String zipCode = scanner.next();  
    	    	Logger.getInstance().write( System.currentTimeMillis() + " " + zipCode + "\n");
    			option4(homeData, zipCode);
    		} else if (userInput == 5) {
    			System.out.println("Please enter the zip code you want the average total livable area for:");
    	    	String zipCode = scanner.next();    
    	    	Logger.getInstance().write( System.currentTimeMillis() + " " + zipCode + "\n");
    			option5(homeData, popData, zipCode);
    		} else if (userInput == 6) {
    			option6(RatioViolationsPerMarketValue, PopulationTickets, homeData, popData, ticketData );
    		} else if (userInput == 0) {
    			break;
    		}
    		
    	}


    }
    
    public static void option6(HashMap<String, Double>  RatioViolationsPerMarketValue, HashMap<String, Double> PopulationTickets, ArrayList<Home> homeData, ArrayList<Population> popData, ArrayList<Ticket>  ticketData) {
    	
    	if (RatioViolationsPerMarketValue.size() == 0) {
    		
    		if (PopulationTickets.size() == 0) {    	
    	    	//for each zipcode go through all of the tickets
    	    	for(Population p : popData) {    		
    	    		for(Ticket t : ticketData) {
    	    			if (t.getZipCode().equals(p.getZipCode())) {
    	    				p.increaseNumTickets();
    	    			}
    	    		} 
    	    		PopulationTickets.put(p.getZipCode(), p.ticketsPerCapita());
    	    	}
        	}
    		    	
			AnalyzeHomeMarketValue mv = new AnalyzeHomeMarketValue();
		
    		
    		for(Population p : popData) {
    			//get the avg market value for this zip or
    			//just reference a previous calculation if it has already been done
    			int avgMarketValue;
			   if (mv.hasInformation(p.getZipCode())) {				  
				  avgMarketValue = mv.getAvgMarketValue(p.getZipCode());
			   } else {				  
				   mv.analyzeData(homeData,p.getZipCode());
				   avgMarketValue = mv.getAvgMarketValue(p.getZipCode());
			   }
			   
			   //parking violations per avgmarket value
			   double violationPerAvg = p.getNumTickets() / (double) avgMarketValue ;
			   //0 / 0 shows inf so fix the value if either one is 0
			   if (avgMarketValue == 0 || p.getNumTickets() == 0) {
				   violationPerAvg = 0;
			   }
			   RatioViolationsPerMarketValue.put(p.getZipCode(), violationPerAvg);
    		}
    		
    		
    	} 
    	
    	
    	//go through all of the keys and print them out (they are the zipcode)
    	//and use they to get the ticket info associated with them
    	for (String zip : RatioViolationsPerMarketValue.keySet()) {
    		 System.out.printf("%s %.4f\n",zip,RatioViolationsPerMarketValue.get(zip));
    	}

    }
    
   public static void option5(ArrayList<Home> homeData, ArrayList<Population> popData, String zipCode ) {
    	
    	//find the population of the zipcode indicated
	   int pop = 0;;
	   for(Population p : popData) {
		   if (p.getZipCode().equals(zipCode)) {
			   pop = p.getPopulation();
			   break;
		   }
	   }
	   
	   AnalyzeHomeMarketValue mv = new AnalyzeHomeMarketValue();
	   int totalMarketValue;
	   if (mv.hasInformation(zipCode)) {		 
		  totalMarketValue = mv.getTotalMarketValue(zipCode);
	   } else {
		   mv.analyzeData(homeData, zipCode);
		   totalMarketValue = mv.getTotalMarketValue(zipCode);
	   }
	   
	   System.out.println(totalMarketValue / pop);
	   
	   
   }

    
    public static void option4(ArrayList<Home> homeData, String zipCode ) {
    	
   
    	
    	AnalyzeHomeLivableArea mv = new AnalyzeHomeLivableArea();
 	   int avgLivable;
 	   if (mv.hasInformation(zipCode)) {
 		 
 		 avgLivable = mv.getAvgLivable(zipCode);
 	   } else {
 	
 		   mv.analyzeData(homeData, zipCode);
 		  avgLivable = mv.getAvgLivable(zipCode);
 	   }
 	   
 	   System.out.println(avgLivable);
 	   
    }

    
    //option 3
    /*user enters a zipcode
     * need the properties data
     * Avg market value  toatl market value for all homes in the zip / number of homes
     * 
     */
    
    public static void option3(ArrayList<Home> homeData, String zipCode ) {
    	
    		
    	AnalyzeHomeMarketValue mv = new AnalyzeHomeMarketValue();
    	System.out.println(mv.analyzeData(homeData, zipCode));   	
    	
    	
    }
    
    
    public static void option2(ArrayList<Population> popData , ArrayList<Ticket> ticketData, HashMap<String, Double> PopulationTickets ) {

        //*********************************** User Input #2 *******************************************//
    	//add up all tickets for each zipcode
    	//only calculate the tickets per capita if it hasnt been done yet
    	if (PopulationTickets.size() == 0) {    	
	    	//for each zipcode go through all of the tickets
	    	for(Population p : popData) {    		
	    		for(Ticket t : ticketData) {
	    			if (t.getZipCode().equals(p.getZipCode())) {
	    				p.increaseNumTickets();
	    			}
	    		} 
	    		PopulationTickets.put(p.getZipCode(), p.ticketsPerCapita());
	    	}
    	}
      	
    	//go through all of the keys and print them out (they are the zipcode)
    	//and use they to get the ticket info associated with them
    	for (String zip : PopulationTickets.keySet()) {
    		 System.out.printf("%s %.4f\n",zip,PopulationTickets.get(zip));
    	}
    	
     

    }
    
    public static void option1(ArrayList<Population> popData) {

        //*********************************** User Input #1 *******************************************//
          
        System.out.println(Population.getTotalPopulation());
        
    }

}
