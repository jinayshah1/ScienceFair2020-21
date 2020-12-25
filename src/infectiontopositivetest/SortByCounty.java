package infectiontopositivetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SortByCounty {
	public static void main(String[] args) throws IOException {
		process();
	}
	public static void process() throws IOException{
		Path path = Paths.get("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily.csv");
	    BufferedReader reader = Files.newBufferedReader(path);
	    String line;
	    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Jinay Shah\\Documents\\NJRSF\\us-counties-daily-sortedbycounty.csv"));
	    
	    while((line=reader.readLine()) != null) {
	    	String[] arr = line.split(",");
	    	
	    	
	    }
	    
	    
	   // writer.write();
        writer.newLine();
	}
}
