package infectiontopositivetest;

import java.io.IOException;

public class TransmissionRate {
	public static void main(String[] args) throws IOException {
		/* First configure for reading the file that was created in the DailyTotals.java class, use that website
		 * 
		 * Have to sort that file by the county number so that it runs through each count
		 * 
		 * Split everything and isolate the case totals, date, and county code from the data
		 * 
		 * Use nested hashmap to store this information for future reference
		 *  
		 * Create a list of dates after which covid cases will go up, maintain as a string in the same format as stored in 
		 * the hashmaps, a string 
		 * 
		 *  Create a separate method that will look for spikes in cases somehow, more detail on this later, just get the
		 *  rest of the framework up and running first before this
		 *  
		 *  Once you determine the average transmission time for all of those holidays, average that out, and using a separate 
		 *  nested hashmap, bind the county name and number to the transmission time, and write this to a .csv file*/
	}
	
}
