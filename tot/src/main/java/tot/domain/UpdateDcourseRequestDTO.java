package tot.domain;

import java.util.List;

public class UpdateDcourseRequestDTO {

	private List<Integer> courIds;
	private List<String> dcourses;
	private Long tripId;

	public UpdateDcourseRequestDTO() {
	}

	public UpdateDcourseRequestDTO(List<Integer> courIds, List<String> dcourses, Long tripId) {
		this.courIds = courIds;
		this.dcourses = dcourses;
		this.tripId = tripId;
	}

	public List<Integer> getCourIds() {
		return courIds;
	}

	public void setCourIds(List<Integer> courIds) {
		this.courIds = courIds;
	}

	public List<String> getDcourses() {
		return dcourses;
	}

	public void setDcourses(List<String> dcourses) {
		this.dcourses = dcourses;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

}
