package logging;

import java.io.FileWriter;
import java.io.IOException;

public class Logger
{ 
    // static variable single_instance of type Singleton 
    private static Logger single_instance = null; 
  
    // variable of type String 
    private String fileName;
  
    // private constructor restricted to this class itself 
    private Logger(String fN) 
    { 
        fileName = fN;
    } 
  
    public static void createInstance(String fileName) {
    	single_instance = new Logger(fileName);
    }
    
    // static method to create instance of Singleton class 
    public static Logger getInstance() 
    { 
       return single_instance; 
    } 
    
    public void write(String line) {
    	try {
    		FileWriter outFile = new FileWriter(fileName,true);
			outFile.write(line);
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
} 