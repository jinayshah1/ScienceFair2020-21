package dailycasetotals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import infectiontopositivetest.DateCountyKey;

public class DailyTotals {

	public static void main(String[] args) throws IOException {
		process();
	}

	public static void process() throws IOException {
		FileReader fileReader = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties.csv");
	    BufferedReader reader = new BufferedReader(fileReader);
	    BufferedReader reader1 = new BufferedReader(fileReader);
	    ArrayList<String> dates = new ArrayList<String>();
	    String line = null;
	    String daily = null;
	    int counter = 0;
	    int prevcase = 0;
	    int curcase = 0;
	    int dailycase = 0;
	    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv"));
	    Map<String, String> countyrecentcases = new HashMap<>();
	    Map<String, String> safecounties = new HashMap<>();
	   
	    
	    System.out.println("Starting while loop 2.....");
	   
	    while((line = reader.readLine()) != null){    	
	    	counter++;
	    	String[] arr = line.split(",");
	    	String toCheck = "";
	    	//Anything that is not the very first line
	    	if(counter>1) {	    		
	    		//When a county already in countyrecentcases shows up
	    		if(arr[3] == null || arr[3].equals("")) {
		    		if(arr[1].equals("Unknown"))
		    			continue;
		    		else
		    			toCheck = arr[1];
		    	} 
		    	else {
		    		toCheck = arr[3];
		    	}
	    		if(countyrecentcases.get(toCheck) != null) {
		    		prevcase = Integer.parseInt(countyrecentcases.get(toCheck));
			    	curcase = Integer.parseInt(arr[4]);
			    	dailycase = curcase-prevcase;		    	
			    	countyrecentcases.put(toCheck, arr[4]);
			    	arr[4] = Integer.toString(dailycase);
			    	
			    	String[] dateReMaker = arr[0].split("-");
			    	arr[0] = dateReMaker[1] + "/" + dateReMaker[2] + "/" + dateReMaker[0];
			    	
			    	daily = String.join(",", arr);		    			    				   
			        writer.write(daily);
			        writer.newLine();
	    		}
	    		//For the first time any county shows up
	    		else {
	    			dailycase = Integer.parseInt(arr[4]);	    			
			    	countyrecentcases.put(toCheck, arr[4]);
			    	
	    			
	    			String[] dateReMaker = arr[0].split("-");
			    	arr[0] = dateReMaker[1] + "/" + dateReMaker[2] + "/" + dateReMaker[0];
			    	
			    	daily = String.join(",", arr);		    			    	
			        writer.write(daily);
			        writer.newLine();
	    		}
	    	}
	    	//For header line
	    	else {    		
	    		daily = String.join(",", arr);		    			    	
		        writer.write(daily);
		        writer.newLine();
	    	}
	        
	    }
	    writer.close();
	    System.out.println("While loop complete....");
	}

}
