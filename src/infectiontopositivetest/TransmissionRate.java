package infectiontopositivetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
		 *  nested hashmap, bind the county name and number to the transmission time, and write this to a .csv file */
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
	    	
	    	int[]arrHash = new int[arr.length];
	    	for(int i = 0; i < arr.length; i++) {
	    		int hold = Integer.parseInt(arr1[i]);
	    		arrHash[i] = hold;
	    	}
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
	    int holder = 0;
	    int curDay = 0;
	    int curMonth = 0;
	    int curYear = 0;
	    int temp = 0;
	    //This will make the code find the average "average" days before and after the date of interest
	    int avg = 3;
	    // Dates forward that the code will look
	    int datFor = 18;
	    Map<String /*county code*/, Map<String /*reference date*/ , Map<String/*root date that values are being pulled from*/, 
	    Map<String/*date of case*/, String/*number cases*/>>>> countyRefDatCases = new HashMap<>();
	    
	    while ((date=reader1.readLine()) != null) {	    	
	    	String[] arrSplit = date.split(",");
	    	Map<String, Map<String, Map<String, String>>> refDatCases = new HashMap<>();
	    	int[]arr = new int[arrSplit.length];
	    	for(int i = 0; i < arrSplit.length; i++) {
	    		int convert = Integer.parseInt(arrSplit[i]);
	    		arr[i] = convert;
	    	}
	    	curMonth = arr[1];
	    	curDay = arr[2];
	    	curYear = arr[3];
	    	while((countyInfo=reader.readLine()) != null) {
	    		String[] arr1 = countyInfo.split(",");
	    		Map<String, String> countiesFound = new HashMap<>();
	    		if(!(countiesFound.containsKey(arr1[3]))) {	    		
		    		Map<String, String> casesForStat = new HashMap<>();
		    		Map<String, Map<String, String>> forwardCases = new HashMap<>();
		    		countiesFound.put(arr1[3], "done");
		    		for(int i = 0; i < datFor; i++) {
		    			for(int j = 0; j < avg; j++) {
		    				if(j!=0) {
		    					if((arr[1] == 12) && arr[2] > 31-avg) {
		    						//add one to the year after it turns to the next year, and make month 1
		    						if((curDay > (31-avg)) && (curDay <= 31)) {
			    						holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay++;
		    						}
		    						else {
		    							curDay = 1+temp;
		    							holder = curDay;
		    							posCase = getCases(1, holder, arr[3]+1, arr1[3], data);
		    	    					casesForStat.put(makeDate(1, holder, arr[3]+1), posCase);
		    							holder = arr[2] - j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay++;
		    							temp++;
		    							
		    						}
		    						
		    					}
		    					else if((arr[1] == 1) && (arr[2] <= avg)) {
		    						//same as above, but subtract to find negative data
		    						if((curDay > 0) && (curDay <= arr[2])) {
			    						holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay--;
		    						}
		    						else {
		    							curDay = 31-temp;
		    							holder = curDay;
		    							posCase = getCases(12, holder, arr[3]-1, arr1[3], data);
		    	    					casesForStat.put(makeDate(12, holder, arr[3]-1), posCase);
		    							holder = arr[2] + j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay--;
		    							temp++;
		    						}
		    						
		    					}
		    					else if((arr[1] == 2) && (arr[2] > 28-avg)) {
		    						//like first if but with February
		    						if((curDay > (28-avg)) && (curDay <= 28)) {
			    						holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay++;
		    						}
		    						else {
		    							curDay = 1+temp;
		    							holder = curDay;
		    							posCase = getCases(arr[1]+1, holder, arr[3], arr1[3], data);
		    	    					casesForStat.put(makeDate(arr[1]+1, holder, arr[3]), posCase);
		    							holder = arr[2] - j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay++;
		    							temp++;
		    						}
		    					}
		    					else if((arr[1] == 3) && (arr[2] <= avg)) {
		    						//like second one
		    						if((curDay > 0) && (curDay <= arr[2])) {
		    							holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay--;
		    						}
		    						else {
		    							curDay = 28-temp;
		    							holder = curDay;
		    							posCase = getCases(2, holder, arr[3], arr1[3], data);
		    	    					casesForStat.put(makeDate(12, holder, arr[3]), posCase);
		    							holder = arr[2] + j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay--;
		    							temp++;
		    							
		    						}
		    					}
		    					else if(((arr[1] == 1) || 
		    							(arr[1] == 3) || 
		    							(arr[1] == 5) || 
		    							(arr[1] == 7) || 
		    							(arr[1] == 8) ||
		    							(arr[1] == 10)) && (arr[2] > 31-avg)) {
		    						//for days with 31 months going forward to the next month
		    						if((curDay > (31-avg)) && (curDay <= 31)) {
			    						holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay++;
		    						}
		    						else {
		    							curDay = 1+temp;
		    							holder = curDay;
		    							posCase = getCases(arr[1]+1, holder, arr[3], arr1[3], data);
		    	    					casesForStat.put(makeDate(arr[1]+1, holder, arr[3]), posCase);
		    							holder = arr[2] - j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay++;
		    							temp++;
		    						}
		    						
		    					}
		    					else if(((arr[1] == 2) || 
		    							(arr[1] == 4) || 
		    							(arr[1] == 6) || 
		    							(arr[1] == 8) || 
		    							(arr[1] == 9) ||
		    							(arr[1] == 11) && (arr[2] <= avg))) {
		    						//for days in the beginning of months before months with 31 days
		    						if((curDay > 0) && (curDay <= arr[2])) {
		    							holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay--;
		    						}
		    						else {
		    							curDay = 31-temp;
		    							holder = curDay;
		    							posCase = getCases(2, holder, arr[3], arr1[3], data);
		    	    					casesForStat.put(makeDate(12, holder, arr[3]), posCase);
		    							holder = arr[2] + j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay--;
		    							temp++;		    							
		    						}
		    					}
		    					else if(((arr[1] == 4) || 
		    							(arr[1] == 6) || 
		    							(arr[1] == 9) || 
		    							(arr[1] == 11) && (arr[2] > 30-avg))) {
		    						//for days with 30 days, looking forward to the next month
		    						if((curDay > (30-avg)) && (curDay <= 30)) {
			    						holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay++;
		    						}
		    						else {
		    							curDay = 1+temp;
		    							holder = curDay;
		    							posCase = getCases(arr[1]+1, holder, arr[3], arr1[3], data);
		    	    					casesForStat.put(makeDate(arr[1]+1, holder, arr[3]), posCase);
		    							holder = arr[2] - j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay++;
		    							temp++;
		    						}
		    						
		    					}
		    					else if(((arr[1] == 5) || 
		    							(arr[1] == 7) || 
		    							(arr[1] == 10) || 
		    							(arr[1] == 12) && (arr[2] <= avg))) {
		    						//for days in the beginning of months before months with 30 days
		    						if((curDay > 0) && (curDay <= arr[2])) {
		    							holder = arr[2]+j;
			    						posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    						holder = arr[2]-j;
			    						negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    						curDay--;
		    						}
		    						else {
		    							curDay = 30-temp;
		    							holder = curDay;
		    							posCase = getCases(2, holder, arr[3], arr1[3], data);
		    	    					casesForStat.put(makeDate(12, holder, arr[3]), posCase);
		    							holder = arr[2] + j;
		    							negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    					casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
		    							curDay--;
		    							temp++;
		    							
		    						}
		    					}
		    					else {
		    						//normal, just add and subtract from the date
			    					holder = arr[2]+j;
			    					posCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    				casesForStat.put(makeDate(arr[1], holder, arr[3]), posCase);
			    					holder = arr[2]-j;
			    					negCase = getCases(arr[1], holder, arr[3], arr1[3], data);
				    				casesForStat.put(makeDate(arr[1], holder, arr[3]), negCase);
			    					curDay++;
		    					}
		    				}
		    				else {
		    					cases = getCases(arr[1], arr[2], arr[3], arr1[3], data);
		    					casesForStat.put(makeDate(arr[1], arr[2], arr[3]), cases);
		    				}
		    			}
		    			forwardCases.put(String.valueOf(i), casesForStat);
		    		}
		    		refDatCases.put(makeDate(arr[1], arr[2], arr[3]), forwardCases);
		    		countyRefDatCases.put(arr1[3], refDatCases);
	    		}
	    	}
	    }
	    statistics(countyRefDatCases);	    
	}
	
	
	public static void /*Map<String, Map<String, Map<String, DateStatistics>>>*/ statistics(Map<String, Map<String, Map<String, Map<String, String>>>> data) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-transmissionrate.csv"));
		Path path = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
	    BufferedReader reader = Files.newBufferedReader(path);
		Path path1 = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\reference-dates.csv");
	    BufferedReader reader1 = Files.newBufferedReader(path1);
	    //Map<String /*county code*/, Map<String /*reference date*/, Map<String /*dates forward from the reference date*/, DateStatistics /*need slope, mean absolute deviation for each date(as another hash map)*/ >>> temp = new HashMap<>();	   
	    String slope = null;
	    String mad = null;
	    String var = null;
	    String sd = null;
	    Map<Integer, Double> mslope = new HashMap<>();
	    Map<Integer, Double> mmean = new HashMap<>();
	    int varSum = 0;
	    int madSum = 0;
	    String countyInfo = null;
	    String refDate = null;
	    double sum = 0;
	    double avg = 0;
	    
		while((countyInfo=reader.readLine()) != null) {
    		String[] arr1 = countyInfo.split(",");
    		Map<String, String> countiesFound = new HashMap<>();
    		Map<String, Map<String, DateStatistics>> refForwardStats = new HashMap<>();
			Map<String, DateStatistics> statsFromCases = new HashMap<>();
    		Map<String, Map<String, Map<String, String>>> temp1 = data.get(arr1[3]);
    		Map<String, Map<String, String>> temp2 = new HashMap<>();
    		Map<String, String> temp3 = new HashMap<>();
    		double tranTime = 0;
    		int counter = 0;
    		if(!(countiesFound.containsKey(arr1[3]))) {
    			while((refDate=reader1.readLine()) != null) {    				   				
    				String[] arr = refDate.split(",");
    				String refDateS = arr[1] + "," + arr[2] + "," + arr[3];   		
    				temp2 = temp1.get(refDateS);
	    			for(int i = 0; i < temp2.size(); i++) {		    				
	    				temp3 = temp2.get(String.valueOf(i));
	    				String[] calcArr = new String[temp3.size()];
	    				Collection<String> list = temp3.values();
	    				calcArr = list.toArray(calcArr);
	    				//find average
	    	    		for(int j = 0; j < calcArr.length; j++) {
	    	    			sum+=Double.parseDouble(calcArr[j]);
	    	    		}
	    	    		avg = sum/calcArr.length;
	    	    		slope = String.valueOf((Double.parseDouble(calcArr[(calcArr.length)-1]) - Double.parseDouble(calcArr[0]))/(calcArr.length));
	    	    		//find MAD/variance/standard deviation
	    	    		for(int k = 0; k < calcArr.length; k++) {
	    	    			madSum += Math.abs(avg-Double.parseDouble(calcArr[k]));
	    	    			varSum += Math.pow(avg-Double.parseDouble(calcArr[k]), 2);
	    	    		}
	    	    		sum=0;
	    	    		int temprr = 1;
	    	    		for(int t = 0; t < calcArr.length; t++) {
	    	    			sum+=Double.parseDouble(calcArr[t]);
	    	    			if((t==2) || (t==5) || (t==8)){
	    	    				mslope.put(temprr, (Double.parseDouble(calcArr[t]) - Double.parseDouble(calcArr[t-2]))/(3));
	    	    				mmean.put(temprr, sum/3);
	    	    				sum=0;
	    	    				temprr++;
	    	    			}
	    	    		}
	    	    		mad = String.valueOf(madSum/calcArr.length);
	    	    		var = String.valueOf(varSum/calcArr.length);
	    	    		sd = String.valueOf(Math.sqrt(varSum/calcArr.length));
	    	    		DateStatistics hold = new DateStatistics(slope, mad, var, sd, i, temp3, mslope, mmean);
	    	    		statsFromCases.put(String.valueOf(i), hold);
	    	    	}
	    			refForwardStats.put(String.valueOf(counter), statsFromCases);
	    			counter++;
    			}
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
    					double twothreediff = mmeanHash.get(3)-mmeanHash.get(2);
    					double locSD = Double.parseDouble(stats.getStandDev());
    					if((onetwodiff > locSD) && (twothreediff > locSD)) {
    						if((onetwodiff > prevDiff1) && (twothreediff >= prevDiff2)){
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
    			double sum2 = 0;
				for(int j = 0; j < tranArr.length; j++) {
	    			sum2+=tranArr[j];
	    		}
				tranTime = sum2/(double)tranArr.length;
    			writer.write(arr1[1] + "," + arr1[2] + "," + arr1[3] + "," + tranTime);
    		}
	    }
	}	
	public static String getCases(int month, int day, int year, String countyCode,Map<DateCountyKey, String> data) {
		DateCountyKey findCases = new DateCountyKey(month, day, year, countyCode);
		return data.get(findCases); 
	}
	
	public static String makeDate(int month, int day, int year) {
		String smonth = String.valueOf(month);
		String sday = String.valueOf(day);
		String syear = String.valueOf(year);
		return smonth + "," + sday + "," + syear;
	}
	
}
