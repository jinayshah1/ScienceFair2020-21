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
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (this.getClass() != other.getClass()) {
			return false;
		}
		DateCountyKey otherObj = (DateCountyKey) other;
 		if ((this.caseMonth == otherObj.getCaseMonth()) &&
			(this.caseDay == otherObj.getCaseDay()) &&
			(this.caseYear == otherObj.getCaseYear()) &&
			(this.countyCode.equals(otherObj.getCountyCode()))) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		int result = 13;
		result = result * this.caseMonth;
		result = result * this.caseDay;
		result = result * this.caseYear;
		result = result + (this.countyCode != null ? this.countyCode.hashCode() : 0);
		return result;
	}
	
	public String toString() {
		return this.caseMonth+ "/" +this.caseDay+ "/" +this.caseYear+ "," + this.countyCode;		
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
