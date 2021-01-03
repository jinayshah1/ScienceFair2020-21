package infectiontopositivetest;

public class DateCountyKey {
	String caseMonth;
	String caseDay;
	String caseYear;
	String countyCode;

	public DateCountyKey(String caseMonth, String caseDay, String caseYear, String countyCode) {
		this.caseMonth = caseMonth;
		this.caseDay = caseDay;
		this.caseYear = caseYear;
		this.countyCode = countyCode;
	}
	
	public boolean equals(DateCountyKey other) {
		if (this.caseMonth.equals(other.getCaseMonth()) &&
			this.caseDay.equals(other.getCaseDay()) &&
			this.caseYear.equals(other.getCaseYear()) &&
			this.countyCode.equals(other.getCountyCode())) {
			return true;
		}
		return false;
	}
	
	public String getCountyCode() {
		return countyCode;
	}

	public String getCaseMonth() {
		return caseMonth;
	}

	public void setCaseMonth(String caseMonth) {
		this.caseMonth = caseMonth;
	}

	public String getCaseDay() {
		return caseDay;
	}

	public void setCaseDay(String caseDay) {
		this.caseDay = caseDay;
	}

	public String getCaseYear() {
		return caseYear;
	}

	public void setCaseYear(String caseYear) {
		this.caseYear = caseYear;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
}
