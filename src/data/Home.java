package data;

import java.util.ArrayList;

import datamanagement.CSVReader;
import logging.Logger;

public class Home {
	
	String zipCode;
	String marketValue;	
	String totalLivableArea;
	private static int numHomes = 0;
	
	public Home(String zipCode, String marketValue, String totalLivableArea) {
		
		this.zipCode = zipCode;
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getMarketValue() {
		return marketValue;
	}
	
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	
	public String getTotalLivableArea() {
		return totalLivableArea;
	}
	
	public void setTotalLivableArea(String totalLivableArea) {
		this.totalLivableArea = totalLivableArea;
	}
	
	 public static ArrayList<Home> loadFromCSV(String filename){
	    	//"population.txt";
		 Logger.getInstance().write(System.currentTimeMillis() + " " + filename + "\n");
	        ArrayList<Home> homeData = new ArrayList<>();
	    
	         
	         CSVReader reader = new CSVReader(filename);
	         String line;
	         reader.openCSVFile();
	         
	         //first line is the header
	         int indexZip= 0;
	         int indexMarket= 0;
	         int indexLivable= 0;
	         reader.hasDataLeft();
	         String firstLine =  reader.getNextLine();
	         //split at a comma BUT ONLY IF IT IS NOT IN QUOTES
	         String[] values = firstLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	         for (int i = 0; i < values.length; i++) {
				if (values[i].equals("zip_code")) {
					indexZip = i;
				} else if ( values[i].equals("total_livable_area")) {
					indexLivable = i;
				} else if ( values[i].equals("market_value")) {
					indexMarket = i;
				}
			}
	         	            
	         while (reader.hasDataLeft()) {
	         	line = reader.getNextLine();
	            
	             String[] splitAtComma = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	             String zipCode = splitAtComma[indexZip];
	             String totalLivable = splitAtComma[indexLivable];
	             String marketValue = splitAtComma[indexMarket];
	             zipCode = zipCode.substring(0,5);
	             Home newHome = new Home(zipCode,marketValue,totalLivable);
	             homeData.add(newHome);   
	             
	             //testing print
	             //System.out.println(newHome.getZipCode() + " " + newHome.getMarketValue() + " " + newHome.getTotalLivableArea());
	             
	            
	         }
	           
	         return homeData;

	       
	    }


	
	
	
	

}
//type_heater,unfinished,unit,utility,view_type,year_built,year_built_estimate,zip_code,zoning,objectid,lat,lng
/*int number_of_rooms;
String assessment_date;
String beginning_point;
String book_and_page;
String building_code;
String building_code_description;
String category_code;
String category_code_description;
String census_tract;
String central_air;
String cross_reference;
String date_exterior_condition;
String depth,exempt_building;
String exempt_land;
String exterior_condition;
String fireplaces;
String frontage;
String fuel,garage_spaces;
String garage_type;
String general_construction;
String geographic_ward;
String homestead_exemption;
String house_extension;
String house_number;
String interior_condition;
String location;
String mailing_address_1;
String mailing_address_2;
String mailing_care_of;
String mailing_city_state;
String mailing_street;*/
/*String market_value_date;
String number_of_bathrooms;
String number_of_bedrooms;
String basements;
String number_stories;
String off_street_open;
String other_building;
String owner_1;
String owner_2;
String parcel_number;
String parcel_shape;
String quality_grade;
String recording_date;
String registry_number;
String sale_date;
String sale_price,separate_utilities,sewer,site_type,state_code,street_code,street_designation,street_direction,street_name,suffix,taxable_building,taxable_land,topography,total_area,*/