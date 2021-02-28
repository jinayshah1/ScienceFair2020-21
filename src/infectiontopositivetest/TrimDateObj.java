package infectiontopositivetest;

import java.util.Map;

public class TrimDateObj {
	Map<DateCountyKey, String> map;
	String[] counties;
	Map<String, Integer> fdates;
	
	
	public TrimDateObj(Map<DateCountyKey, String> map, String[] counties, Map<String, Integer> fdates) {
		this.counties = counties;
		this.map = map;
		this.fdates = fdates;
	}	
	public Map<String, Integer> getFdates() {
		return fdates;
	}
	public void setFdates(Map<String, Integer> fdates) {
		this.fdates = fdates;
	}
	public Map<DateCountyKey, String> getMap() {
		return map;
	}
	public void setMap(Map<DateCountyKey, String> map) {
		this.map = map;
	}

	public String[] getCounties() {
		return counties;
	}

	public void setCounties(String[] counties) {
		this.counties = counties;
	}
}
