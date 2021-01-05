package infectiontopositivetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TransmissionRate {
	public static void main(String[] args) throws IOException {
		/* First configure for reading the file that was created in the DailyTotals.java class, use that website - done
		 * 
		 * Split everything and isolate the case totals, date, and county code from the data - done
		 * 
		 * Use ArrayList and CaseLine object to store this information for future reference - done
		 *  
		 * Create a list of dates after which covid cases will go up, maintain as a string in the same format as stored in 
		 * the ArrayList, a string 
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
	    Map<DateCountyKey, String> searchablecases = new HashMap<>();
	    
	    while((line = reader.readLine()) != null) {
	    	counter++;
	    	String[] arr = line.split(",");
	    	String[] arr1 = arr[0].split("/");
	    	
	    	int[]arrHash = null;
	    	for(int i = 0; i < arr.length; i++) {
	    		int hold = Integer.parseInt(arr1[i]);
	    		arrHash[i] = hold;
	    	}
	    	//////////////////////////////// CHECK ABOVE AND BELOW LOGIC//////////////////////////////////////////////
	    	DateCountyKey curLine = new DateCountyKey(arrHash[0], arrHash[1], arrHash[2], arr[3]);
	    	if(counter>1) {
	    		searchablecases.put(curLine, arr[4]);
	    	}
	    }
	    spike(searchablecases);
	    
	}
	
	public static void spike(Map<DateCountyKey, String> data) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-transmissionrate.csv"));
		Path path = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
	    BufferedReader reader = Files.newBufferedReader(path);
		Path path1 = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\reference-dates.csv");
	    BufferedReader reader1 = Files.newBufferedReader(path1);
	    String date = null;
	    String cases = null;
	    String countyInfo = null;
	    String posCase = null;
	    String negCase = null;
	    
	    while ((date=reader1.readLine()) != null) {
	    	String[] arrSplit = date.split(",");
	    	int[]arr;
	    	for(int i = 0; i < arr.length; i++) {
	    		int convert = Integer.parseInt(arrSplit[i]);
	    		arr[i] = convert;
	    	}
	    	while((countyInfo=reader.readLine()) != null) {
	    		String[] arr1 = countyInfo.split(",");
	    		Map<String, String> casesForStat = new HashMap<>();
	    		for(int i = 0; i < 18; i++) {
	    			for(int j = 0; j < 3; j++) {
	    				if(j!=0) {
	    					if((arr[1] == 12) && arr[2] >= 29) {
	    						//add one to the year after it turns to the next year, and make month 1
	    					}
	    					else if((arr[1] == 1) && (arr[2] <= 3)) {
	    						//same as above, but subtract to find negative data
	    					}
	    					else if() {
	    						
	    					}
	    					else {
	    						//normal, just add and subtract from the date
	    					}
	    				}
	    				else {
	    					cases = getCases(arr[1], arr[2], arr[3], arr1[3], data);
	    					casesForStat.put(String.join(",", arr), cases);
	    				}
	    			}
	    		}
	    		
	    	}
	    }
	}
	
	public static String getCases(int month, int day, int year, String countyCode,Map<DateCountyKey, String> data) {
		String cases = "";
		DateCountyKey findCases = new DateCountyKey(month, day, year, countyCode);
		cases = data.get(findCases);
		return cases; 
	}
	
}
