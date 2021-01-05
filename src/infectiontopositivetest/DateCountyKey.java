package infectiontopositivetest;

public class DateCountyKey {
	int caseMonth;
	int caseDay;
	int caseYear;
	String countyCode;

	public DateCountyKey(int caseMonth, int caseDay, int caseYear, String countyCode) {
		this.caseMonth = caseMonth;
		this.caseDay = caseDay;
		this.caseYear = caseYear;
		this.countyCode = countyCode;
	}
	
	public boolean equals(DateCountyKey other) {
		if ((this.caseMonth == other.getCaseMonth()) &&
			(this.caseDay == other.getCaseDay()) &&
			(this.caseYear == other.getCaseYear()) &&
			(this.countyCode == other.getCountyCode())) {
			return true;
		}
		return false;
	}
	
	public String getCountyCode() {
		return countyCode;
	}

	public int getCaseMonth() {
		return caseMonth;
	}

	public void setCaseMonth(int caseMonth) {
		this.caseMonth = caseMonth;
	}

	public int getCaseDay() {
		return caseDay;
	}

	public void setCaseDay(int caseDay) {
		this.caseDay = caseDay;
	}

	public int getCaseYear() {
		return caseYear;
	}

	public void setCaseYear(int caseYear) {
		this.caseYear = caseYear;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
}
