package infectiontopositivetest;

import java.util.Map;

public class DateStatistics {
	String slope;
	String meanAbsDev;
	String standDev;
	String var;
	int datForw;
	Map<String, String> cases;
	Map<Integer, Double> mslope;
	Map<Integer, Double> mmean;

	public DateStatistics(String slope, String meanAbsDev, String var, String standDev, int datForw, Map<String, String> cases, Map<Integer, Double> mslope, Map<Integer, Double> mmean) {
		this.meanAbsDev = meanAbsDev;
		this.slope = slope;	
		this.standDev = standDev;
		this.var = var;
		this.datForw = datForw;
		this.cases = cases;
		this.mslope = mslope;
		this.mmean = mmean;
	}

	public Map<String, String> getCases() {
		return cases;
	}

	public void setCases(Map<String, String> cases) {
		this.cases = cases;
	}

	public Map<Integer, Double> getMslope() {
		return mslope;
	}

	public void setMslope(Map<Integer, Double> mslope) {
		this.mslope = mslope;
	}

	public Map<Integer, Double> getMmean() {
		return mmean;
	}

	public void setMmean(Map<Integer, Double> mmean) {
		this.mmean = mmean;
	}

	public String getSlope() {
		return slope;
	}

	public int getDatForw() {
		return datForw;
	}

	public void setDatForw(int datForw) {
		this.datForw = datForw;
	}

	public void setSlope(String slope) {
		this.slope = slope;
	}

	public String getMeanAbsDev() {
		return meanAbsDev;
	}

	public void setMeanAbsDev(String meanAbsDev) {
		this.meanAbsDev = meanAbsDev;
	}

	public String getStandDev() {
		return standDev;
	}

	public void setStandDev(String standDev) {
		this.standDev = standDev;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
}
