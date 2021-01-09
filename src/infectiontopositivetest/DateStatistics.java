package infectiontopositivetest;

import java.util.Map;

public class DateStatistics {
	String countyCode;
	String slope;
	Map<String, String> meanAbsDev;

	public DateStatistics(String countyCode, String slope, Map<String, String> meanAbsDev) {
		this.meanAbsDev = meanAbsDev;
		this.countyCode = countyCode;
		this.slope = slope;		
	}
}
