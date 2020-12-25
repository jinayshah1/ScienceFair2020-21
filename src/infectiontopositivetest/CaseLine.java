package infectiontopositivetest;

public class CaseLine {
	String caseDate;
	String countyCode;
	String cases;
	
	public CaseLine(String caseDate, String countyCode, String cases) {
		this.caseDate = caseDate;
		this.countyCode = countyCode;
		this.cases = cases;
	}
	
	public String getCaseDate() {
		return caseDate;
	}
	
	public String getCountyCode() {
		return countyCode;
	}
	
	public String getCases() {
		return cases;
	}	
	
}
