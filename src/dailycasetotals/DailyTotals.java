package dailycasetotals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DailyTotals {

	public static void main(String[] args) throws IOException {
		process();
	}

	public static void process() throws IOException {
        URL cumulativecases = new URL("https://raw.githubusercontent.com/nytimes/covid-19-data/master/us-counties.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(cumulativecases.openStream()));
	    String line = null;
	    String daily = null;
	    int counter = 0;
	    int prevcase = 0;
	    int curcase = 0;
	    int dailycase = 0;
	    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv"));
	    Map<String, String> countyrecentcases = new HashMap<>();
	    
	    System.out.println("Starting while loop.....");
	   
	    while((line = reader.readLine()) != null){    	
	    	counter++;
	    	String[] arr = line.split(",");
	    	
	    	//Anything that is not the very first line
	    	if(counter>1) {
	    		//When a county already in countyrecentcases shows up
	    		if(countyrecentcases.get(arr[3]) != null) {
		    		prevcase = Integer.parseInt(countyrecentcases.get(arr[3]));
			    	curcase = Integer.parseInt(arr[4]);
			    	dailycase = curcase-prevcase;		    	
			    	countyrecentcases.put(arr[3], arr[4]);
			    	arr[4] = Integer.toString(dailycase);
			    	daily = String.join(",", arr);		    			    				   
			        writer.write(daily);
			        writer.newLine();
	    		}
	    		//For the first time any county shows up
	    		else {
	    			dailycase = Integer.parseInt(arr[4]);
	    			countyrecentcases.put(arr[3], arr[4]);		    	
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
