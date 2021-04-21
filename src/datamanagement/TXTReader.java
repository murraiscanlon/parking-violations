package datamanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import data.*;
public class TXTReader {
	
	BufferedReader br;
	String nextLine;
	
	public boolean openTXTFile(String filename) {
		
		try {
			br = new BufferedReader((new FileReader(filename)));
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
	
    /*public ArrayList<Population> loadTxtData(String filename) {
        //"population.txt";
        String line = "";
        ArrayList<Population> txtData = new ArrayList<>();

        {
            try {
                //br = new BufferedReader((new FileReader(filename)));

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(" ");
                    String zipData = values[0];
                    int populationPerZip = Integer.parseInt(values[1]);
                    Population populationData = new Population(zipData, populationPerZip);
                    txtData.add(populationData);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return txtData;
        }*/

   // }
    



}




