package infectiontopositivetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SafeCounties {
	public static void main(String[] args) throws IOException {
		System.out.println("processing started....");
		process();
		System.out.println("....processing complete");
	}
	private static void myPrint(Object obj) {
		System.out.println(obj);
	}
	public static void process() throws IOException{
		try {
			FileReader fileReader = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
			FileReader fileReader1 = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
		    BufferedReader reader = new BufferedReader(fileReader);
		    BufferedReader reader1 = new BufferedReader(fileReader1);
		    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-scounties-daily.csv"));

		    
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
					String temps = null;
			    	if(arr[3] == null || arr[3].equals("")) {
			    		if(arr[1].equals("Unknown"))
			    			continue;
			    		else
			    			temps = arr[1];
			    	} 
			    	else {
			    		temps = arr[3];
			    	}
					if(newString(temps, counties)) {
						counties[ccounterr] = temps;
						ccounterr++;
					}
		    	}	
				counterr++;
		    }
		    reader1.close();
		    myPrint("found all counties and dates");
		    myPrint(counties.length);
		    myPrint(dates.length);
		    while((line = reader.readLine()) != null) {	    	
		    	if(counter != 0) {
			    	String[] arr = line.split(",");
			    	String[] arr1 = arr[0].split("/");
			    	
			    	int[]arrHash = new int[3];
			    	for(int i = 0; i < arrHash.length; i++) {
			    		int hold = (int)Integer.valueOf(arr1[i]);
			    		arrHash[i] = hold;
			    	}
			    	DateCountyKey curLine = null;
			    	if(arr[3] == null || arr[3].equals("")) {
			    		if(arr[1].equals("Unknown"))
			    			continue;
			    		else
			    			curLine = new DateCountyKey(arrHash[0], arrHash[1], arrHash[2], arr[1]);
			    	} 
			    	else {
			    		curLine = new DateCountyKey(arrHash[0], arrHash[1], arrHash[2], arr[3]);
			    	}
			    	
		    		searchablecases.put(curLine, arr[4]);
			    	
		    	}
		    	counter++;
		    }
		    reader.close();
		    myPrint("process method complete --> trim time");
		    TrimDateObj tdo = trimData(searchablecases, dates, counties);
		    Map<DateCountyKey, String> data = tdo.getMap();
		    Map<String, Integer> fdates = tdo.getFdates();
		    for(int i = 0; i < counties.length; i++) {
		    	if(counties[i] == "") {
		    		break;
		    	}
		    	if(fdates.get(counties[i]) == null) {
		    		break;
		    	}
		    	int fdat = fdates.get(counties[i]);
		    	
		    	for(int k = fdat; k < dates.length; k++) {
		    		if(dates[k] == "") {
		    			break;
		    		}
		    		String darr[] = dates[k].split("/");		    	
			    	int[]arrHash = new int[3];
			    	for(int j = 0; j < arrHash.length; j++) {	
			    		if(darr[j] == "") {
			    			break;
			    		}
			    		arrHash[j] = (int)Integer.valueOf(darr[j]);
			    	}	    		
		    		DateCountyKey curLine = new DateCountyKey(arrHash[0], arrHash[1], arrHash[2], counties[i]);
		    		writer.write(arrHash[0] + "/" +arrHash[1]+ "/" +arrHash[2]+ "," +counties[i]+ "," +data.get(curLine));
		    		writer.newLine();
		    	}
		    }
		    writer.flush();
		    writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		   
		}
		
		public static TrimDateObj trimData(Map<DateCountyKey, String> searchablecases, String[] datestc, String[] countiestc) throws IOException {
			FileReader fileReader = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
		    BufferedReader reader = new BufferedReader(fileReader);
			Map<DateCountyKey, String> map = new HashMap<>();
			Map<String, Integer> fdates = new HashMap<>();
			String read = "";
			boolean check = true;
			myPrint(countiestc.length);	
			int coounter = 1;
			for(int i = 0; i < countiestc.length; i++) {
				if(countiestc[i] == "") {
					break;
				}
				FileReader fileReader1 = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
				BufferedReader reader1 = new BufferedReader(fileReader1);
				String firDat = null;
				int firDatIdx = 0;
				int counter = 0;
				while((read = reader1.readLine()) != null) {
					//myPrint("Finding first date.");
					if(counter > 0) {
						String[] arr = read.split(",");
						String temps = null;				
				    	if(arr[3] == null || arr[3].equals("")) {
				    		if(arr[1].equals("Unknown"))
				    			temps = arr[2];
				    		else
				    			temps = arr[1];
				    	} 
				    	else {
				    		temps = arr[3];
				    	}
				    	if(temps.equals(countiestc[i])) {
				    		firDat = arr[0];
				    		String[] datarr= firDat.split("/");
				    		int[]idatarr = new int[3];
					    	for(int k = 0; k < idatarr.length; k++) {
					    		if(datarr[k] == "") {
					    			break;
					    		}
					    		int hold = (int)Integer.valueOf(datarr[k]);
					    		idatarr[k] = hold;
					    	}
				    		if(idatarr[2] > 2020) {
				    			firDat = "4/1/2020";
				    		}
				    		else if(idatarr[2] == 2020 && idatarr[0] > 4) {
				    			firDat = "4/1/2020";
				    		}
				    		else if(idatarr[2] == 2020 && idatarr[0] == 4 && idatarr[1] > 1) {
				    			firDat = "4/1/2020";
				    		}
				    		for(int y = 0; y < datestc.length; y++) {
				    			if(datestc[y].equals(firDat)) {
				    				firDatIdx = y;
				    				fdates.put(temps, firDatIdx);
						    		break;
				    			}				    			
				    		}
				    		break;
				    	}				    	
					}
					counter++;
				}
				reader1.close();
				for(int j = firDatIdx; j < datestc.length; j++) {
					if(datestc[j] == "") {
						break;
					}
					String tempr = datestc[j];
					String[] arr = tempr.split("/");
					int[]arrHash = new int[3];
			    	for(int k = 0; k < arrHash.length; k++) {
			    		int hold = (int)Integer.valueOf(arr[k]);
			    		arrHash[k] = hold;
			    	}
					DateCountyKey dck = new DateCountyKey(arrHash[0], arrHash[1], arrHash[2], countiestc[i]);
					if(searchablecases.get(dck) == null) {
						check = false;
					}
				}
				if(check = true) {
					for(int l = firDatIdx; l < datestc.length; l++) {
						if(datestc[l] == "") {
							break;
						}
						String[] arr = datestc[l].split("/");
						int[]arrHash = new int[3];
				    	for(int k = 0; k < arrHash.length; k++) {
				    		if(arr[k] == "") {
				    			break;
				    		}
				    		if(arr[k] == null) {
				    			break;
				    		}
				    		int hold = (int)Integer.valueOf(arr[k]);
				    		arrHash[k] = hold;
				    	}
						DateCountyKey dck = new DateCountyKey(arrHash[0], arrHash[1], arrHash[2], countiestc[i]);
						map.put(dck, searchablecases.get(dck));
					}
					myPrint("trim for " +countiestc[i]+ " completed: " +coounter);
				}			
				check = true;
				coounter++;
			}	
			TrimDateObj tdo = new TrimDateObj(map, countiestc, fdates);
			return tdo;
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
