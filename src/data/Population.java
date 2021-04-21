package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import datamanagement.TXTReader;
import logging.Logger;

public class Population implements Comparable< Population > {
    String zipcode;
    int population;
    int numTickets;
    static int TOTAL_POPULATION = 0;


    public Population(String zipcode, int population) {
        this.zipcode = zipcode;
        this.population = population;
        this.numTickets = 0;     

    }
    
    public void increaseNumTickets() {
    	numTickets++;
    }
    
    public int getNumTickets() {
    	return numTickets;
    }
    
    public double ticketsPerCapita() {
    	return (double) numTickets / (double) population;
    }

    public String getZipCode() {
        return zipcode;
    }

    public int getPopulation() {
        return population;
    }

    public void print() {
        System.out.println("zipcode: "+ zipcode + ", " +  "population: " +  population);
    }
    
    public static ArrayList<Population> loadFromTXT(String filename){
    	//"population.txt";
    	Logger.getInstance().write(System.currentTimeMillis() + " " + filename + "\n");
        ArrayList<Population> txtData = new ArrayList<>();
        TXTReader reader = new TXTReader();
        String line;
        reader.openTXTFile(filename);
        TOTAL_POPULATION = 0;
        while (reader.hasDataLeft()) {
        	line = reader.getNextLine();
            String[] values = line.split(" ");
            String zipData = values[0];
            int populationPerZip = Integer.parseInt(values[1]);
            TOTAL_POPULATION += populationPerZip;
            Population populationData = new Population(zipData, populationPerZip);
            txtData.add(populationData);
        }
          
        return txtData;
       
    }
    
    public static int getTotalPopulation() {
    	return TOTAL_POPULATION;
    }


	@Override
	public int compareTo(Population otherPopulation) {
		int zip1 = Integer.valueOf(zipcode);
		int zip2 = Integer.valueOf(otherPopulation.getZipCode());
		//compareTo should return -1 if the item in THIS class should be before the OTHER
		//compareTo should return 0 if they are the same
		//compareTo should return 1 if the item in THIS class should be AFTER the OTHER
		if (zip1 < zip2) {
			return -1;
		} else if(zip1 == zip2) {
			return 0;
		} else {
			return 1;
		}
		
	}
}
