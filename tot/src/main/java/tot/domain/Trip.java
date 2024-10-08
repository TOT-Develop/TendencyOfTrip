package tot.domain;

import java.sql.Timestamp;
import java.util.List;

public class Trip {
	private int tripId;
	private String memId;
	private String areaCode;
	private String tripName;
	private int trAmt;
	private Timestamp trstaDate;
	private Timestamp trendDate;
	private int trPeople;
	private String trImgpath;
	private List<CourseDTO> courses;

	public Trip() {
	}

	public List<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDTO> courses) {
		this.courses = courses;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public int getTrAmt() {
		return trAmt;
	}

	public void setTrAmt(int trAmt) {
		this.trAmt = trAmt;
	}

	public Timestamp getTrstaDate() {
		return trstaDate;
	}

	public void setTrstaDate(Timestamp trstaDate) {
		this.trstaDate = trstaDate;
	}

	public Timestamp getTrendDate() {
		return trendDate;
	}

	public void setTrendDate(Timestamp trendDate) {
		this.trendDate = trendDate;
	}

	public int getTrPeople() {
		return trPeople;
	}

	public void setTrPeople(int trPeople) {
		this.trPeople = trPeople;
	}

	public String getTrImgpath() {
		return trImgpath;
	}

	public void setTrImgpath(String trImgpath) {
		this.trImgpath = trImgpath;
	}

	public Trip(int tripId, String memId, String areaCode, String tripName, int trAmt, Timestamp trstaDate,
			Timestamp trendDate, int trPeople, String trImgpath, List<CourseDTO> courses) {
		super();
		this.tripId = tripId;
		this.memId = memId;
		this.areaCode = areaCode;
		this.tripName = tripName;
		this.trAmt = trAmt;
		this.trstaDate = trstaDate;
		this.trendDate = trendDate;
		this.trPeople = trPeople;
		this.trImgpath = trImgpath;
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Trip [tripId=" + tripId + ", memId=" + memId + ", areaCode=" + areaCode + ", tripName=" + tripName
				+ ", trAmt=" + trAmt + ", trstaDate=" + trstaDate + ", trendDate=" + trendDate + ", trPeople="
				+ trPeople + ", trImgpath=" + trImgpath + ", courses=" + courses + "]";
	}

}
