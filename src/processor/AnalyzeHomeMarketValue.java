package processor;

import java.util.ArrayList;
import java.util.HashMap;

import data.Home;
import data.Population;
import datamanagement.AnalyzeHomeInterface;

public class AnalyzeHomeMarketValue implements AnalyzeHomeInterface{
	
	
	private static HashMap<String, Integer> avgMarketValues = new HashMap<String, Integer>();
	private static HashMap<String, Integer> totalMarketValues = new HashMap<String, Integer>();
	
		
	public boolean hasInformation(String zipcode) {
		return avgMarketValues.containsKey(zipcode);
	}
	
	@Override
	public int analyzeData(ArrayList<Home> homeData, String zipCode) {
		
		int totalMarketValue = 0;
		int numHomesInZip = 0;
    	//in every zip code go through every house
    	for(Home h : homeData) {
    		//if the house we're on match the zip code we're in add to the total market value
    		//and increase number of homes
    		if (h.getZipCode().equals(zipCode)) {
    			totalMarketValue += Integer.valueOf(h.getMarketValue());
    			numHomesInZip++;
    		}
    	
    	}
    	
    	int avg;
    	//at this point we have the total market value and num homes FOR THIS ZIP
    	if (numHomesInZip == 0) {
    		avg = 0;
    	} else {
    		avg =  totalMarketValue / numHomesInZip;
    	}
    	avgMarketValues.put(zipCode,avg);
		totalMarketValues.put(zipCode,totalMarketValue);
    	
    	return avg;
	
		
	}
	
	public int getTotalMarketValue(String zipCode) {		
		return totalMarketValues.get(zipCode);
	}
	
	public int getAvgMarketValue(String zipCode) {		
		return avgMarketValues.get(zipCode);
	}
	
	


}
