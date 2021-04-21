package processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.Population;

public class statisticsCalculator {

    //User input #2
        
	//User input #1
    public static int calcTotalPop(ArrayList<Population> popData){
        int totalPopulation = 0;
        for (Population p : popData){
            totalPopulation += p.getPopulation();
        }
        return totalPopulation;
    }



}
