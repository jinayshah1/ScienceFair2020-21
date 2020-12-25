package infectiontopositivetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TransmissionRate {
	public static void main(String[] args) throws IOException {
		/* First configure for reading the file that was created in the DailyTotals.java class, use that website - done
		 * 
		 * Split everything and isolate the case totals, date, and county code from the data - done
		 * 
		 * Use ArrayList and CaseLine object to store this information for future reference - done
		 *  
		 * Create a list of dates after which covid cases will go up, maintain as a string in the same format as stored in 
		 * the hashmaps, a string 
		 * 
		 *  Create a separate method that will look for spikes in cases somehow, more detail on this later, just get the
		 *  rest of the framework up and running first before this
		 *  
		 *  Once you determine the average transmission time for all of those holidays, average that out, and using a separate 
		 *  nested hashmap, bind the county name and number to the transmission time, and write this to a .csv file*/
		process();
	}
	public static void process() throws IOException{
		Path path = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
	    BufferedReader reader = Files.newBufferedReader(path);
	    String line = null;
	    int counter = 0;
	    ArrayList <CaseLine> list = new ArrayList <CaseLine>();
	    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-transmissionrate.csv"));
	    
	    while((line = reader.readLine()) != null) {
	    	counter++;
	    	String[] arr = line.split(",");
	    	CaseLine curLine = new CaseLine(arr[0], arr[3], arr[4]);
	    	//Anything that is not the very first line
	    	if(counter>1) {
	    		list.add(curLine);
	    	}
	    }
	    
	}
	
	public static void spike() throws IOException{
		
	}
	
}
