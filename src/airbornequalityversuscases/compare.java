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

import infectiontopositivetest.DateCountyKey;

public class compare {
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
			FileReader fileReader = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-sdaily.csv");
			FileReader fileReader1 = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-transmissionrate.csv");
			FileReader fileReader2 = new FileReader("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\AFRPM2.5.csv");
		    BufferedReader reader = new BufferedReader(fileReader);
		    BufferedReader reader1 = new BufferedReader(fileReader1);
		    BufferedReader reader2 = new BufferedReader(fileReader2);
		    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\statistics.csv"));

		    
		    String line = null;
		    int counter = 0;
		    Map<DateCountyKey, String> searchablecases = new HashMap<>();
		    Map<DateCountyKey, String> searchableaq = new HashMap<>();
		    Map<String, String> searchabletrate = new HashMap<>();
		    
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
		    while((line = reader1.readLine()) != null) {
		    	//t rate
		    }
		    while((line = reader2.readLine()) != null) {
		    	//aq
		    }
		    //System.out.println(searchablecases);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		}
}
