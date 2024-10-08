package tot.domain;

public class CourseUpdateRequest {
	private String courId;
	private int tripId;
	private String targetId;
	private int index;

	public String getCourId() {
		return courId;
	}

	public void setCourId(String courId) {
		this.courId = courId;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}