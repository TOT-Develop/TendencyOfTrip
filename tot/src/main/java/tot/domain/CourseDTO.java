package tot.domain;

import java.sql.Timestamp;
import java.util.List;

public class CourseDTO {

	private int courId; // 코스 아이디
	private int tripId; // 여행 아이디
	private String areaCode; // 지역 코드
	private String dCourse; // 하루 코스 설명
	private List<CourseResDTO> courseDetail; // 하루 여행 상세 코스
	private Timestamp courRegdate; // 등록 일시
	private Timestamp courUpdate; // 수정 일시
	private String regionName; // 여행지역 이름
	private String regionImageUrl; // 지역 이미지 URL

	public CourseDTO() {
	}

	public CourseDTO(int courId, int tripId, String areaCode, String dCourse, List<CourseResDTO> courseDetail,
			Timestamp courRegdate, Timestamp courUpdate, String regionName, String regionImageUrl) {
		this.courId = courId;
		this.tripId = tripId;
		this.areaCode = areaCode;
		this.dCourse = dCourse;
		this.courseDetail = courseDetail;
		this.courRegdate = courRegdate;
		this.courUpdate = courUpdate;
		this.regionName = regionName;
		this.regionImageUrl = regionImageUrl;
	}

	public int getCourId() {
		return courId;
	}

	public void setCourId(int courId) {
		this.courId = courId;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getdCourse() {
		return dCourse;
	}

	public void setdCourse(String dCourse) {
		this.dCourse = dCourse;
	}

	public List<CourseResDTO> getCourseDetail() {
		return courseDetail;
	}

	public void setCourseDetail(List<CourseResDTO> courseDetail) {
		this.courseDetail = courseDetail;
	}

	public Timestamp getCourRegdate() {
		return courRegdate;
	}

	public void setCourRegdate(Timestamp courRegdate) {
		this.courRegdate = courRegdate;
	}

	public Timestamp getCourUpdate() {
		return courUpdate;
	}

	public void setCourUpdate(Timestamp courUpdate) {
		this.courUpdate = courUpdate;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionImageUrl() {
		return regionImageUrl;
	}

	public void setRegionImageUrl(String regionImageUrl) {
		this.regionImageUrl = regionImageUrl;
	}

	@Override
	public String toString() {
		return "CourseDTO [courId=" + courId + ", tripId=" + tripId + ", areaCode=" + areaCode + ", dCourse=" + dCourse
				+ ", courseDetail=" + courseDetail + ", courRegdate=" + courRegdate + ", courUpdate=" + courUpdate
				+ ", regionName=" + regionName + ", regionImageUrl=" + regionImageUrl + "]";
	}

}
