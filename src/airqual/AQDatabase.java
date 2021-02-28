/**
 * 
 */
package airqual;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * @author pranav
 *
 */
public class AQDatabase {
	
	public static void main(String[] args) {
		process();
	}
	
	public static void process() {
		ArrayList<String[]> data = new ArrayList<String[]>();
	    BufferedReader linkReader;
	    FileWriter outputfile = null;
	    BufferedWriter writer = null;
	    JSONObject afrData = null;
	    System.out.println("Starting for loop....");
	    String stateCode = "";
	    ArrayList<String> counties = new ArrayList<String>();
	    String written = null;
       
		try {
			outputfile = new FileWriter("AFRDatabasePM2.5states1_4.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    for(int i=1;i<58;i++)
	    {
	    	writer = new BufferedWriter(outputfile);
	    	written = null;
	    	System.out.println(i);
	    	if(i==3||i==7||i==14||i==43||i==52)
	    	{
	    		i+=1;
	    	}
	    	
	    	if(i<10)
    	    {
    		    stateCode = "0"+i; 
    	    }
    	    else if(i==57)
    	    {
    	    	stateCode = "72";
    	    }
    	    else if(i>=10)
    	    {
    	    	stateCode = ""+i;
    	    }
	    	for(int c=0;c<1;c++)
    	    {
	    		String web = null;
	    		if(c==0)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200201 + "&edate=" + 20200228 +"&state=" + stateCode;
	    		}
	    		else if(c==1)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200301 + "&edate=" + 20200331 +"&state=" + stateCode;
	    		}
	    		else if(c==2)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200401 + "&edate=" + 20200430 +"&state=" + stateCode;
	    		}
	    		else if(c==3)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200501 + "&edate=" + 20200531 +"&state=" + stateCode;
	    		}
	    		else if(c==4)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200601 + "&edate=" + 20200630 +"&state=" + stateCode;
	    		}
	    		else if(c==5)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200701 + "&edate=" + 20200731 +"&state=" + stateCode;
	    		}
	    		else if(c==6)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200801 + "&edate=" + 20200830 +"&state=" + stateCode;
	    		}
	    		else if(c==7)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20200901 + "&edate=" + 20200930 +"&state=" + stateCode;
	    		}
	    		else if(c==8)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20201001 + "&edate=" + 20201031 +"&state=" + stateCode;
	    		}
	    		else if(c==9)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20201101 + "&edate=" + 20201130 +"&state=" + stateCode;
	    		}
	    		else if(c==10)
	    		{
	    			web = "https://aqs.epa.gov/data/api/dailyData/byState?email=vanarp1808@gmail.com&key=russetfox23&param=88101&bdate=" + 20201201 + "&edate=" + 20201231 +"&state=" + stateCode;
	    		}
	    		System.out.println(new Date() + " >> " + c + " url:" + web);
	    	    StringBuilder storage = new StringBuilder();
	    	    HttpURLConnection conLink;
			    try {
			      URL link = new URL(web);
			      conLink = (HttpURLConnection) link.openConnection();
			      conLink.setReadTimeout(900000);
			      conLink.setConnectTimeout(900000);
			      conLink.connect();			      
			      BufferedInputStream inStream = new BufferedInputStream(conLink.getInputStream());
			      byte dataBuffer[] = new byte[2048];
			      int bytesRead = -1;
			      String tempFile = "C:\\Users\\Jinay Shah\\Documents\\NJRSF\\tmp-123.json";
			      FileOutputStream fos = new FileOutputStream(tempFile);
			      BufferedOutputStream dest = new BufferedOutputStream(fos);
			      
			      while ((bytesRead = inStream.read(dataBuffer)) != -1) {
			    	//  storage.append(new String(dataBuffer));
			    	  dest.write(dataBuffer, 0, bytesRead);
			      }
			      dest.flush();
			      dest.close();
			      System.out.println(new Date() + ":: done with " + web);
			      conLink.disconnect();
			      
			      BufferedReader fileReader = new BufferedReader(new FileReader(tempFile));
			      String line = null;
			      while ((line = fileReader.readLine()) != null) {
			    	  storage.append(line);
			      }
			      fileReader.close();
			      afrData = new JSONObject(storage.toString());
			      storage = new StringBuilder();
			    } catch (Exception e) {
			    	e.printStackTrace();
		        }
			    try {
		    	    int len = afrData.getJSONArray("Data").length();
		    	    for(int j = 0;j<len;j++)
		    	    {
		    		    String a[]  = new String [6];
						a[0] = afrData.getJSONArray("Data").getJSONObject(j).getString("state");
						a[1] = String.valueOf(afrData.getJSONArray("Data").getJSONObject(j).getDouble("arithmetic_mean"));
						a[2] = afrData.getJSONArray("Data").getJSONObject(j).getString("date_local");
						a[4] = "";//String.valueOf(afrData.getJSONArray("Data").getJSONObject(j).getInt("cbsa_code"));
						a[5] = afrData.getJSONArray("Data").getJSONObject(j).getString("county");
						data.add(a);
		    	    }
			    }catch(JSONException e)
			    {
			    	e.printStackTrace();
			    }
			    
			    System.out.println("For loop complete....");
		        System.out.println("Starting Average Calculations....");     
		        
		        while(countyCheck(counties,data)!=null)
		        {
		        	double mean = 0;
			        double sum = 0;
			    	double num = 0;
			    	double diff = 0;
			    	double sumdiff = 0;
			    	int p = 0;
			    	int arrNum = 0;
			    	double standev = 0;
			    	double standiff = 0;
			        String temp[];
			        String check = countyCheck(counties,data);
		        	while(p<countyNum(check,data))
		        	{
		        		temp = data.get(arrNum);
		        		if(temp[5].equalsIgnoreCase(check))
		        		{
		        			sum+=stringToDoub(temp[1]);
		        			num++;
		        			p++;
		        			arrNum++;
		        		}
		        		else
		        		{
		        			arrNum++;
		        		}
		        	}
		        	p=0;
		        	arrNum=0;
		        	mean = sum/num;
		        	while(p<countyNum(check,data))
		        	{
		        		temp = data.get(arrNum);
		        		if(temp[5].equalsIgnoreCase(check))
		        		{
		        			double value = stringToDoub(temp[1]);
		        			diff = Math.abs(mean-value);
		        			sumdiff+=(diff*diff);
		        			p++;
		        			arrNum++;
		        		}
		        		else
		        		{
		        			arrNum++;
		        		}
		        	}
		        	p=0;
		        	arrNum=0;
		        	standev = Math.sqrt(sumdiff/num);
		        	while(p<countyNum(check,data))
		        	{
		        		temp = data.get(arrNum);
		        		if(temp[5].equalsIgnoreCase(check))
		        		{
		        			standiff = Math.abs(standev-stringToDoub(temp[1]));
		        			if(standiff>standev)
		        			{
		        				temp[3] = "change";
		        				data.set(arrNum, temp);
		        			}
	        				arrNum++;
	        				p++;
		        		}
	        			else 
	        			{
	        				arrNum++;
	        			}
		        	}
		        	counties.add(check);
		       	}
		        try {
					Thread.sleep(2000); //sleep for 5 seconds before starting next file
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		        
    	    }
    	    
	        System.out.println(new Date() + ":: Average State Calculations complete....");	
	        try {
				Thread.sleep(5000); //sleep for 5 seconds before starting next file
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }


        written = "State,AFR,Local Date,Significant Change,,County";
        try {
			writer.write(written);
			writer.newLine();
		}catch (IOException e) {
			e.printStackTrace();
		}
        counties.clear();
        ArrayList<String> countDate = new ArrayList<String>(); 
        for(int i=0;i<data.size();i++)
        {
        	String [] temp = data.get(i);
        	String l = (temp[5]+temp[2]);
        	if(inCounties(l,countDate))
        	{
        		data.remove(i);
        		i--;
        	}
        	else
        	{
        		countDate.add(l);
        	}
        }
    	for(int k=0;k<data.size();k++)
    	{
    		if(data.get(k)!=null)
    		{
    			written = String.join(",", data.get(k));	
	    		try {
	    			writer.write(written);
	    			writer.newLine();
	    		}catch (IOException e) {
	    			e.printStackTrace();
	    		}
    		}
    	}
	    try {
	    	writer.flush();
			writer.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}   	    	    	    	    	    	 

	public static Integer stringToInt(String p)
	{
		String clean = p.replaceAll("\\D+","");
		p = clean;
		Integer b = Integer.parseInt(p);
		return b;
	}
	
	public static Double stringToDoub(String p)
	{
		double l = Double.parseDouble(p);
		return l;
	}
	
	public static String countyCheck(ArrayList<String> a,ArrayList<String[]> b)
	{
		int check = 0;
		for(int i=0;i<b.size();i++)
		{
			check = 0;
			String [] t = b.get(i);
			for(int j=0;j<a.size();j++)
			{
				if(a.get(j).equalsIgnoreCase(t[5]))
				{
					check = 1;
					break;
				}
			}
			if(check!=1)
			{
				return t[5];
			}
		}
		return null;
	}
	
	public static int countyNum(String c,ArrayList<String[]> d)
	{
		int result = 0;
		for(int i=0;i<d.size();i++)
		{
			String[] t = d.get(i);
			if(c.equalsIgnoreCase(t[5]))
			{
				result++;
			}
		}
		return result;
	}
	
	public static boolean inCounties(String n,ArrayList<String> c)
	{
		boolean b = false;
		for(int i=0;i<c.size();i++)
		{
			if(n.equalsIgnoreCase(c.get(i)))
			{
				b = true;
				break;
			}
		}
		return b;
	}
	
}


