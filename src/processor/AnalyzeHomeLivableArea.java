package processor;

import java.util.ArrayList;
import java.util.HashMap;

import data.Home;
import datamanagement.AnalyzeHomeInterface;

public class AnalyzeHomeLivableArea implements AnalyzeHomeInterface{
	
	private static HashMap<String, Integer> avgLivableValues = new HashMap<String, Integer>();

	public boolean hasInformation(String zipcode) {
		return avgLivableValues.containsKey(zipcode);
	}
	
	@Override
	public int analyzeData(ArrayList<Home> homeData, String zipCode) {
		
		int totalLivableArea = 0;
		int numHomesInZip = 0;
    	//in every zip code go through every house
    	for(Home h : homeData) {
    		//if the house we're on match the zip code we're in add to the total market value
    		//and increase number of homes
    		if (h.getZipCode().equals(zipCode)) {
    			totalLivableArea += Integer.valueOf(h.getTotalLivableArea());
    			numHomesInZip++;
    		}
    	
    	}
    	//at this point we have the total market value and num homes FOR THIS ZIP
    	int avg = 0;
    	if (numHomesInZip > 0) {    		
    		avg = totalLivableArea / numHomesInZip;
    	}
    	
    	avgLivableValues.put(zipCode,avg);
    	return avg;  	
    	
    	
	
		
	}

	public int getAvgLivable(String zipCode) {		
		return avgLivableValues.get(zipCode);
	}

}
