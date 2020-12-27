package infectiontopositivetest;

public class DateCountyKey {
	String caseDate;
	String countyCode;
	
	public DateCountyKey(String caseDate, String countyCode) {
		this.caseDate = caseDate;
		this.countyCode = countyCode;
	}
	
	public String getCaseDate() {
		return caseDate;
	}
	
	public String getCountyCode() {
		return countyCode;
	}
	
}
