package infectiontopositivetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;


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
		 *  nested hashmap, bind the county name and number to the transmission time, and write this to a .csv file */
		System.out.println("processing started....");
		process();
		/*
		DateCountyKey dck1 = new DateCountyKey(11, 12, 2020, "34023");
		DateCountyKey dck2 = new DateCountyKey(11, 12, 2020, "34023");
		myPrint("dck1 == dck2 :" + (dck1 == dck2));
		myPrint("dck1.equals(dck2) :" + (dck1.equals(dck2)));
		myPrint("dck1, dck2 hashcode:" + dck1.hashCode() + " " + dck2.hashCode());
		myPrint("dck1, dck2" + (dck1) + " " + dck2);
		
		HashMap<DateCountyKey, String> testMap = new HashMap();
		testMap.put(dck1, "ONE");
		testMap.put(dck2, "TWO");
		myPrint(testMap);
		myPrint(testMap.get(dck2));
		myPrint(testMap.get(dck1));
		DateCountyKey dck3 = new DateCountyKey(11, 12, 2020, "34023");
		myPrint(testMap.get(dck3));
*/
		System.out.println("....processing complete");
	}
	
	private static void myPrint(Object obj) {
		System.out.println(obj);
	}
	public static void process() throws IOException{
		try {
		FileReader fileReader = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-scounties-daily.csv");
		FileReader fileReader1 = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-scounties-daily.csv");
	    BufferedReader reader = new BufferedReader(fileReader);
	    BufferedReader reader1 = new BufferedReader(fileReader1);

	    
	    String line = null;
	    int counter = 0;
	    Map<DateCountyKey, String> searchablecases = new HashMap<>();
	    String[] dates = new String[500];
	    Arrays.fill(dates, "");
	    String[] counties = new String[5000];
	    Arrays.fill(counties, "");
	    int counterr = 0;
	    int counterrr = 0;
	    int ccounterr = 0;
	    while((line = reader1.readLine()) != null) {
	    	if(counterr != 0) {
		    	String[] arr = line.split(",");
				if(newString(arr[0], dates)) {
		    		dates[counterrr] = arr[0];
		    		counterrr++;
		    	}		
				if(newString(arr[1], counties)) {
					counties[ccounterr] = arr[1];
					ccounterr++;
				}
	    	}	
			counterr++;
	    }
	    reader1.close();
	    myPrint("found all counties and dates");
	    
	    while((line = reader.readLine()) != null) {	    	
	    	if(counter != 0) {
		    	String[] arr = line.split(",");
		    	String[] arr1 = arr[0].split("/");
		    	
		    	int[]arrHash = new int[3];
		    	for(int i = 0; i < arrHash.length; i++) {
		    		int hold = (int)Integer.valueOf(arr1[i]);
		    		arrHash[i] = hold;
		    	}
		    	DateCountyKey curLine = new DateCountyKey(arrHash[0], arrHash[1], arrHash[2], arr[1]);		    		    	
	    		searchablecases.put(curLine, arr[2]);
		    	
	    	}
	    	counter++;
	    }
	    //System.out.println(searchablecases);
	    reader.close();
	    Map<String, Integer> fill = new HashMap<>();
	    TrimDateObj tdo = new TrimDateObj(searchablecases, counties, fill);
	    spike(tdo);
		} catch (Exception e) {
			e.printStackTrace();
		}	    
	}
	
	public static void spike(TrimDateObj tdo) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-transmissionrate.csv"));
		//Path path = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
	    //BufferedReader reader = Files.newBufferedReader(path);
		//Path path1 = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\reference-dates.csv");
	    //BufferedReader reader1 = Files.newBufferedReader(path1);
		
	    FileReader fileReader1 = new FileReader("C:\\\\Users\\\\Jinay Shah\\\\Documents\\\\NJRSF\\\\reference-dates.csv");
	    BufferedReader reader1 = new BufferedReader(fileReader1);
		
	    String date = null;
	    String cases = null;
	    String countyInfo = null;
	    String posCase = null;
	    String negCase = null;
	    int holder = 0;
	    int curDay = 0;
	    int fcurDay = 0;
	    int bcurDay = 0;
	    int curMonth = 0;
	    int curYear = 0;
	    int day = 0;
	    int temp = 0;
	    //This will make the code find the average "average" days before and after the date of interest
	    int avg = 4;
	    // Dates forward that the code will look
	    int datFor = 14;
	    int count = 0;
    	Map<DateCountyKey, Map<String, CaseDateArrays>> refDatCases = new HashMap<>();
	    String[] scounties = tdo.getCounties();
	    Map<DateCountyKey, String> data = tdo.getMap();
    	
	    while ((date=reader1.readLine()) != null) {	    	
	    	String[] arrSplit = date.split(",");
	    	int[]arr = new int[arrSplit.length];
	    	for(int i = 0; i < arrSplit.length; i++) {
	    		if(i != 0) {
		    		arr[i] = Integer.parseInt(arrSplit[i]);
	    		}
	    		else {
	    			arr[i] = 0;
	    		}
	    	}
	    	System.out.println(arr[1] + " " + arr[2] + " " + arr[3]);
	    	curMonth = arr[1];
	    	curDay = arr[2];
	    	curYear = arr[3];
	    	FileReader fileReader = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-scounties-daily.csv");
		    BufferedReader reader = new BufferedReader(fileReader);
		    count = 0;
		    int counter = 0;
	    	for(int x = 0; x < scounties.length; x++) {	    		
	    		if(scounties[x] == "") {
	    			break;
	    		}
	    		count++;    	
	    		Map<String, String> countiesFound = new HashMap<>();
	    		String ckey = scounties[x];
	    		if(!(countiesFound.containsKey(ckey))) {	    			    		
		    		Map<String, CaseDateArrays> forwardCases = new HashMap<>();
		    		countiesFound.put(ckey, "done");
		    		// TODO 
		    		// TODO
		    		// double check storing dates for the sort, make sure it is the same dates that are being asked for
		    		//
		    		// 
		    		//
		    		// go back and test with counties that are unknown or have only Puerto Rico, or Guam etc
		    		//
		    		// another thing is that might have to check if there is data for the county in the time frame before
		    		// filling stuff
		    		//
		    		// add functionality for leap years
		    		//
		    		// TODO
		    		// TODO
		    		for(int i = 0; i <= datFor; i++) {
		    			if((arr[1] == 12) && (arr[2]+i > 31)) {
		    				curMonth = 1;
			    			curDay = arr[2]+i-31;
			    			curYear = arr[3]+1;
			    			day = curDay;
		    			}
		    			else if((arr[1] == 2) && (arr[3]%4 == 0) && (arr[2]+i > 29)) {
		    				curMonth = arr[1]+1;
			    			curDay = arr[2]+i-29;
			    			curYear = arr[3];
			    			day = curDay;
		    			}
		    			else if((arr[1] == 2) && (arr[2]+i > 28)) {
		    				curMonth = arr[1]+1;
			    			curDay = arr[2]+i-28;
			    			curYear = arr[3];
			    			day = curDay;
		    			}
		    			else if((arr[1] == 1 || 
		    					arr[1] == 3 || 
		    					arr[1] == 5 || 
		    					arr[1] == 7 || 
		    					arr[1] == 8 ||
		    					arr[1] == 10) && (arr[2]+i > 31)) {
		    				curMonth = arr[1]+1;
			    			curDay = arr[2]+i-31;
			    			curYear = arr[3];
			    			day = curDay;
		    			}
		    			else if((arr[1] == 4 || 
		    					arr[1] == 6 || 
		    					arr[1] == 9 || 
		    					arr[1] == 11) && (arr[2]+i > 30)) {
		    				curMonth = arr[1]+1;
			    			curDay = arr[2]+i-30;
			    			curYear = arr[3];
			    			day = curDay;
		    			}		    			
		    			else {
		    				curMonth = arr[1];
			    			curDay = arr[2]+i;
			    			curYear = arr[3];
			    			day = curDay;
		    			}
		    			fcurDay = curDay;
		    			bcurDay = curDay;
		    			int initDay = curDay;
		    			temp = 0;
		    			Map<String, String> casesForStat = new HashMap<>();
		    			ArrayList<String> dates = new ArrayList<String>();		    	
		    			for(int j = 0; j <= avg; j++) {
		    				if(j!=0) {
		    					if((curMonth == 12) && initDay > 31-avg) {
		    						//add one to the year after it turns to the next year, and make month 1
		    						if((fcurDay > (31-avg)) && (fcurDay <= 31)) {
			    						holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						fcurDay++;
		    						}
		    						else {
		    							fcurDay = 1+temp;
		    							holder = fcurDay;
		    							posCase = getCases(1, holder, curYear+1, ckey, data);
		    	    					casesForStat.put(makeDate(1, holder, curYear+1), posCase);
		    	    					dates.add(makeDate(1, holder, curYear+1));
		    							holder = day-j;
		    							negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							fcurDay++;
		    							temp++;
		    							
		    						}
		    						
		    					}
		    					else if((curMonth == 1) && (initDay <= avg)) {
		    						//same as above, but subtract to find negative data
		    						if((bcurDay > 0) && (bcurDay <= initDay)) {
			    						holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						bcurDay--;
		    						}
		    						else {
		    							bcurDay = 31-temp;
		    							holder = bcurDay;
		    							negCase = getCases(12, holder, curYear-1, ckey, data);
		    	    					casesForStat.put(makeDate(12, holder, curYear-1), negCase);
		    	    					dates.add(makeDate(12, holder, curYear-1));
		    							holder = day+j;
		    							posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							bcurDay--;
		    							temp++;
		    						}
		    						
		    					}
		    					else if(curYear % 4 == 0 && curMonth == 2 && initDay > 29-avg) {		    					
		    						//like first if but with February leap year
		    						if((fcurDay > (29-avg)) && (fcurDay <= 29)) {
			    						holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						fcurDay++;
		    						}
		    						else {
		    							fcurDay = 1+temp;
		    							holder = fcurDay;
		    							posCase = getCases(curMonth+1, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(curMonth+1, holder, curYear), posCase);
		    	    					dates.add(makeDate(curMonth+1, holder, curYear));
		    							holder = day-j;
		    							negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							fcurDay++;
		    							temp++;
		    						}
		    					}
		    					else if(curMonth == 2 && initDay > 28-avg) {		    					
		    						//like first if but with February no leap year
		    						if((fcurDay > (28-avg)) && (fcurDay <= 28)) {
			    						holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						fcurDay++;
		    						}
		    						else {
		    							fcurDay = 1+temp;
		    							holder = fcurDay;
		    							posCase = getCases(curMonth+1, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(curMonth+1, holder, curYear), posCase);
		    	    					dates.add(makeDate(curMonth+1, holder, curYear));
		    							holder = day-j;
		    							negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							fcurDay++;
		    							temp++;
		    						}
		    					} 
		    					else if((curYear % 4 == 0) && (curMonth == 3) && (initDay <= avg)) {
		    						//like second one
		    						if((bcurDay > 0) && (bcurDay <= initDay)) {
		    							holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						bcurDay--;
		    						}
		    						else {
		    							bcurDay = 29-temp;
		    							holder = bcurDay;
		    							negCase = getCases(2, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(2, holder, curYear), negCase);
		    	    					dates.add(makeDate(2, holder, curYear));
		    							holder = day + j;
		    							posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							bcurDay--;
		    							temp++;
		    							
		    						}
		    					}
		    					else if((curMonth == 3) && (initDay <= avg)) {
		    						//like second one
		    						if((bcurDay > 0) && (bcurDay <= initDay)) {
		    							holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						bcurDay--;
		    						}
		    						else {
		    							bcurDay = 28-temp;
		    							holder = bcurDay;
		    							negCase = getCases(2, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(2, holder, curYear), negCase);
		    	    					dates.add(makeDate(2, holder, curYear));
		    							holder = day + j;
		    							posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							bcurDay--;
		    							temp++;		    							
		    						}
		    					}
		    					else if(((curMonth == 1) || 
		    							(curMonth == 3) || 
		    							(curMonth == 5) || 
		    							(curMonth == 7) || 
		    							(curMonth == 8) ||
		    							(curMonth == 10)) && (initDay > 31-avg)) {
		    						//for months with 31 days going forward to the next month
		    						if((fcurDay > (31-avg)) && (fcurDay <= 31)) {
			    						holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						fcurDay++;
		    						}
		    						else {
		    							fcurDay = 1+temp;
		    							holder = fcurDay;
		    							posCase = getCases(curMonth+1, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(curMonth+1, holder, curYear), posCase);
		    	    					dates.add(makeDate(curMonth+1, holder, curYear));
		    							holder = day-j;
		    							negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							fcurDay++;
		    							temp++;
		    						}
		    						
		    					}
		    					else if(((curMonth == 2) || 
		    							(curMonth == 4) || 
		    							(curMonth == 6) || 
		    							(curMonth == 8) || 
		    							(curMonth == 9) ||
		    							(curMonth == 11)) && (initDay <= avg)) {
		    						//for days in the beginning of months before months with 31 days
		    						if((bcurDay > 0) && (bcurDay <= initDay)) {
		    							holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						bcurDay--;
		    						}
		    						else {
		    							bcurDay = 31-temp;
		    							holder = bcurDay;
		    							negCase = getCases(curMonth-1, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(curMonth-1, holder, curYear), negCase);
		    	    					dates.add(makeDate(curMonth-1, holder, curYear));
		    							holder = day + j;
		    							posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							bcurDay--;
		    							temp++;		    							
		    						}
		    					}
		    					else if(((curMonth == 4) || 
		    							(curMonth == 6) || 
		    							(curMonth == 9) || 
		    							(curMonth == 11)) && (initDay > 30-avg)) {
		    						//for days with 30 days, looking forward to the next month
		    						if((fcurDay > (30-avg)) && (fcurDay <= 30)) {
			    						holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						fcurDay++;
		    						}
		    						else {
		    							fcurDay = 1+temp;
		    							holder = fcurDay;
		    							posCase = getCases(curMonth+1, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(curMonth+1, holder, curYear), posCase);
		    	    					dates.add(makeDate(curMonth+1, holder, curYear));
		    							holder = day - j;
		    							negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							fcurDay++;
		    							temp++;
		    						}
		    						
		    					}
		    					else if(((curMonth == 5) || 
		    							(curMonth == 7) || 
		    							(curMonth == 10) || 
		    							(curMonth == 12)) && (initDay <= avg)) {
		    						//for days in the beginning of months before months with 30 days
		    						if((bcurDay > 0) && (bcurDay <= initDay)) {
		    							holder = day+j;
			    						posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						holder = day-j;
			    						negCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
			    						bcurDay--;
		    						}
		    						else {
		    							bcurDay = 30-temp;
		    							holder = bcurDay;
		    							negCase = getCases(curMonth-1, holder, curYear, ckey, data);
		    	    					casesForStat.put(makeDate(curMonth-1, holder, curYear), negCase);
		    	    					dates.add(makeDate(curMonth-1, holder, curYear));
		    							holder = day + j;
		    							posCase = getCases(curMonth, holder, curYear, ckey, data);
				    					casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    					dates.add(makeDate(curMonth, holder, curYear));
		    							bcurDay--;
		    							temp++;
		    							
		    						}
		    					}
		    					else {
		    						//normal, just add and subtract from the date
			    					holder = day+j;
			    					posCase = getCases(curMonth, holder, curYear, ckey, data);
				    				casesForStat.put(makeDate(curMonth, holder, curYear), posCase);
				    				dates.add(makeDate(curMonth, holder, curYear));
			    					holder = day-j;
			    					negCase = getCases(curMonth, holder, curYear, ckey, data);
				    				casesForStat.put(makeDate(curMonth, holder, curYear), negCase);
				    				dates.add(makeDate(curMonth, holder, curYear));
			    					fcurDay++;
		    					}
		    				}
		    				else {
		    					cases = getCases(curMonth, day, curYear, ckey, data);
		    					casesForStat.put(makeDate(curMonth, day, curYear), cases);
		    					dates.add(makeDate(curMonth, day, curYear));
		    					fcurDay++;
		    					bcurDay--;
		    				}
		    			}
		    			CaseDateArrays h = sortByDate(casesForStat, dates);
		    			forwardCases.put(String.valueOf(i), h);
		    		}
		    		DateCountyKey id = new DateCountyKey(arr[1], arr[2], arr[3], ckey);
		    		counter++;
		    		refDatCases.put(id, forwardCases);
	    		}
	    		myPrint("county done: " + count);
	    	}
	    	reader.close();
	    	myPrint("Date done.");
	    }
//	    System.out.println(countyRefDatCases);
	    
	    reader1.close();
	    myPrint("spike method complete");
	    statistics(refDatCases);	    
	}
	
	//TODO PROBLEM HERE WHEN SORTING AND THE YEAR IS GREATER, THE ONES WITH GREATER YEAR ARE COMING TO THE FRONT FOR SOME REASON
	public static CaseDateArrays sortByDate(Map<String, String> casesForStat, ArrayList<String> unsDat) {
		String[] udat = unsDat.toArray(String[]::new);
		
		
		int n = udat.length;
		for (int i = 0; i < n-1; i++) 
        { 
            int min_idx = i; 
            for (int j = i+1; j < n; j++) { 
            	String[] scurSplit = udat[j].split(",");
            	String[] sminSplit = udat[min_idx].split(",");
            	int[] curSplit = new int[scurSplit.length];
            	int[] minSplit = new int[sminSplit.length];
            	for(int k = 0; k < scurSplit.length; k++) {
	    			curSplit[k] = Integer.parseInt(scurSplit[k]);
	    			minSplit[k] = Integer.parseInt(sminSplit[k]);
	    		}
            	if(curSplit[2] < minSplit[2]) {
            		min_idx = j;
            	}
            	else if((curSplit[2] == minSplit[2]) && (curSplit[0] < minSplit[0])) {
            		min_idx = j;
            	}
            	else if((curSplit[2] == minSplit[2]) && (curSplit[0] == minSplit[0]) && (curSplit[1] < minSplit[1])) {
            		min_idx = j;
            	}                        	
            }
            String temp = udat[min_idx]; 
            udat[min_idx] = udat[i]; 
            udat[i] = temp; 
        }
		String[] mcases = new String[udat.length];
		for(int i = 0; i < udat.length; i++) {
			if(casesForStat.get(udat[i]) == null) {
				myPrint("Date sort wrong: " + udat[i]);
			}
			mcases[i] = casesForStat.get(udat[i]);
		}
		CaseDateArrays casdat = new CaseDateArrays(udat, mcases);
		
		return casdat;
	}
	public static String[] casesForDays(Map<String, String> cases, String[] sortDat) {
		String[] mcases = new String[sortDat.length];
		for(int i = 0; i < sortDat.length; i++) {
			if(cases.get(sortDat[i]) == null) {
				myPrint("casesForDays error: " + sortDat[i]);
			}
			mcases[i] = cases.get(sortDat[i]);
		}
		return mcases;
	}

	public static void statistics(Map<DateCountyKey, Map<String, CaseDateArrays>> refDatCases) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-transmissionrate.csv"));
		FileReader fileReader = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
	    BufferedReader reader = new BufferedReader(fileReader);	    
	    String countyInfo = null;
	    String refDate = null;
	    double sum = 0;
	    double avg = 0;
	    Map<String, String> countiesFound = new HashMap<>();
	    int counter1 = 0;
	    
		while((countyInfo=reader.readLine()) != null) {
    		String[] arr1 = countyInfo.split(",");
    		
    		Map<String, Map<String, DateStatistics>> refForwardStats = new HashMap<>();
			Map<String, DateStatistics> statsFromCases = new HashMap<>();
    		Map<String, CaseDateArrays> temp2 = new HashMap<>();
    		CaseDateArrays temp3 = null;
    		double tranTime = 0;
    		int counter = 0;   		
    		if(!(countiesFound.containsKey(arr1[3])) && (counter1 != 0)) {
    			FileReader fileReader1 = new FileReader("C:\\\\Users\\\\Jinay Shah\\\\Documents\\\\NJRSF\\\\reference-dates.csv");
    		    BufferedReader reader1 = new BufferedReader(fileReader1);
    			while((refDate=reader1.readLine()) != null) {
    				//myPrint(counter);
    				String[] arr = refDate.split(",");
    				DateCountyKey key = null;
    		    	if(arr1[3] == null || arr1[3].equals("")) {
    		    		if(arr[1].equals("Unknown"))
    		    			key = new DateCountyKey(Integer.valueOf(arr[1]), Integer.valueOf(arr[2]), Integer.valueOf(arr[3]), arr1[2]);
    		    		else
    		    			key = new DateCountyKey(Integer.valueOf(arr[1]), Integer.valueOf(arr[2]), Integer.valueOf(arr[3]), arr1[1]);
    		    	} 
    		    	else {
    		    		key = new DateCountyKey(Integer.valueOf(arr[1]), Integer.valueOf(arr[2]), Integer.valueOf(arr[3]), arr1[3]);
    		    	}
    				temp2 = refDatCases.get(key);
    				if(temp2 == null) {
    					myPrint("Key returning null: " + key);
    				}
    				if(temp2 != null) {
		    			for(int i = 0; i < temp2.size(); i++) {
		    				String slope = null;
		    			    String mad = null;
		    			    String var = null;
		    			    String sd = null;
		    			    Map<Integer, Double> mslope = new HashMap<>();
		    			    Map<Integer, Double> mmean = new HashMap<>();
		    			    int varSum = 0;
		    			    int madSum = 0;
		    				temp3 = temp2.get(String.valueOf(i));
		    				
		    				String[] sdateArr = temp3.getSordates();
		    				String[] scalcArr = temp3.getSorcases();
		    				double[] calcArr = new double[scalcArr.length];		    				
		    				//find average
		    				
		    	    		for(int j = 0; j < scalcArr.length; j++) {
		    	    			if(scalcArr[j] == null) {
		    	    				myPrint("Calculations method: " + scalcArr[j]);
		    	    			}
		    	    			else {
		    	    				calcArr[j] = Double.parseDouble(scalcArr[j]);
		    	    			}
		    	    		}
		    	    		avg = StatUtils.mean(calcArr);
		    	    		SimpleRegression calcSlope = new SimpleRegression();
		    	    		for(int j = 0; j < calcArr.length; j++) {
		    	    			calcSlope.addData((double)j, calcArr[j]);
		    	    		}
		    	    		slope = String.valueOf(calcSlope.getSlope());

		    	    		for(int k = 0; k < calcArr.length; k++) {		    	    			
			    	    		madSum += Math.abs(avg-(calcArr[k]));
		    	    		}
		    	    		sum=0;

		    	    		int temprr = 1;
		    	    		
		    	    		for(int t = 0; t < calcArr.length; t+=3) {
		    	    			SimpleRegression calcmslope = new SimpleRegression();
		    	    			mmean.put(temprr, StatUtils.mean(calcArr, t, 3));
		    	    			for(int l = t; l < t+3; l++) {
		    	    				mslope.put(temprr, calcmslope.getSlope());
		    	    			}
		    	    			temprr++;
		    	    		}
		    	    		mad = String.valueOf(madSum/calcArr.length);
		    	    		var = String.valueOf(StatUtils.variance(calcArr));
		    	    		sd = String.valueOf(Math.sqrt(StatUtils.variance(calcArr)));
		    	    		DateStatistics hold = new DateStatistics(slope, mad, var, sd, i, sdateArr, scalcArr, mslope, mmean);
		    	    		statsFromCases.put(String.valueOf(i), hold);
		    	    	}
		    			refForwardStats.put(String.valueOf(counter), statsFromCases);
    				}
	    			counter++;
    			}//inner while loop
    			reader1.close();
    			double[] tranArr = new double[refForwardStats.size()];
    			for(int i = 0; i < refForwardStats.size(); i++) {
    				statsFromCases = refForwardStats.get(String.valueOf(i));   				
    				double diffTemp = 0;
    				double prevDiff1 = 0;
    				double prevDiff2 = 0;
    				double prevj = 0;
    				for(int j = 0; j < statsFromCases.size(); j++) {
    					DateStatistics stats = statsFromCases.get(String.valueOf(j));
    					Map<Integer, Double> mmeanHash = stats.getMmean();
    					double onetwodiff = mmeanHash.get(2)-mmeanHash.get(1);
    					double twothreediff = mmeanHash.get(2)-mmeanHash.get(3);
    					double locSD = Double.parseDouble(stats.getStandDev())/40;
    					myPrint("Finding spikes error: " + mmeanHash);
    					if((onetwodiff > locSD) && (twothreediff > locSD)) {
    						if((onetwodiff >= prevDiff1) && (twothreediff >= prevDiff2)){
    							diffTemp = j;
    							prevDiff1 = onetwodiff;
    	    					prevDiff2 = twothreediff;
    	    					prevj = j;
    						}    	
    						else if(((onetwodiff > prevDiff1) && (twothreediff < prevDiff2)) || ((onetwodiff < prevDiff1) && (twothreediff > prevDiff2))) {
    							diffTemp = ((double)j+prevj)/2;
    							prevDiff1 = onetwodiff;
    	    					prevDiff2 = twothreediff;
    	    					prevj = j;
    						}
    					}   				
    				}
    				tranArr[i] = diffTemp;	
    			}
    			tranTime = StatUtils.mean(tranArr);
    			writer.write(arr1[1] + "," + arr1[2] + "," + arr1[3] + "," + tranTime);
    			writer.newLine();
    		}//if statement	making sure each county only goes once
    		countiesFound.put(arr1[3], "done");
    		counter1++;
	    } //outer while loop
		reader.close(); 
		writer.flush();
		writer.close();
	}
	
	public static String getCases(int month, int day, int year, String county, Map<DateCountyKey, String> data) {
		DateCountyKey findCases = new DateCountyKey(month, day, year, county);
		String cases = data.get(findCases);
		return cases;
	}
	
	public static String makeDate(int month, int day, int year) {
		if(day > 31 || day < 1) {
			myPrint("Wrong Day: " + day);
		}
		String smonth = String.valueOf(month);
		String sday = String.valueOf(day);
		String syear = String.valueOf(year);
		return smonth + "," + sday + "," + syear;
	}
	
	public static int getCaseIdx(String[] sdates, String date) {
		int idx = 0;
		for(int i = 0; i < sdates.length; i++) {
			if(sdates[i].equals(date)) {
				idx = i;			
			}
		}
		return idx;
		
	}
	
	public static boolean newString(String arr, String[] counties) {
		String[] udat = counties;
		for(int i = 0; i < udat.length; i++) {
			if(udat[i] == null) {
				myPrint(udat[i]);
			}
			else if(udat[i].equals(arr)) {
				myPrint("false");
				return false;
			}
		}
		myPrint("true");
		return true;
	}
	
}
