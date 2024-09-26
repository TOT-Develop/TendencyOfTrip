package tot.domain;

import java.sql.Timestamp;
import java.util.List;

public class CourseDTO {
    
    private int courId;         // 코스 아이디
    private int tripId;         // 여행 아이디
    private String areacode;    // 지역 코드
    private String dcourse;     // 하루 코스 설명 문자열
    private List<CourseResDTO> courseDetail; // 하루 여행 상세 코스
    private Timestamp courRegdate; // 등록일시
    private Timestamp courUpdate;  // 수정일시

    public CourseDTO() {
		// TODO Auto-generated constructor stub
	}
    
    

	public CourseDTO(int courId, int tripId, String areacode, String dcourse, List<CourseResDTO> courseDetail,
			Timestamp courRegdate, Timestamp courUpdate) {
		super();
		this.courId = courId;
		this.tripId = tripId;
		this.areacode = areacode;
		this.dcourse = dcourse;
		this.courseDetail = courseDetail;
		this.courRegdate = courRegdate;
		this.courUpdate = courUpdate;
	}



	public int getCourId() {
		return courId;
	}

	public int getTripId() {
		return tripId;
	}

	public String getAreacode() {
		return areacode;
	}

	public String getDcourse() {
		return dcourse;
	}

	public List<CourseResDTO> getCourseDetail() {
		return courseDetail;
	}

	public Timestamp getCourRegdate() {
		return courRegdate;
	}

	public Timestamp getCourUpdate() {
		return courUpdate;
	}

	public void setCourId(int courId) {
		this.courId = courId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public void setDcourse(String dcourse) {
		this.dcourse = dcourse;
	}

	public void setCourseDetail(List<CourseResDTO> courseDetail) {
		this.courseDetail = courseDetail;
	}

	public void setCourRegdate(Timestamp courRegdate) {
		this.courRegdate = courRegdate;
	}

	public void setCourUpdate(Timestamp courUpdate) {
		this.courUpdate = courUpdate;
	}


	@Override
	public String toString() {
		return "CourseDTO [courId=" + courId + ", tripId=" + tripId + ", areacode=" + areacode + ", dcourse=" + dcourse
				+ ", courseDetail=" + courseDetail + ", courRegdate=" + courRegdate + ", courUpdate=" + courUpdate
				+ "]";
	}
    
	
    
}
