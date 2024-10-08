package tot.domain;

public class TourDTO {
	private String toId; // 관광지 아이디
	private String areacode; // 지역코드
	private String tourType; // 관광지 유형코드
	private String toName; // 관광지명
	private String toAddress; // 주소
	private String toDetailAddress; // 상세주소
	private String toTime; // 운영시간
	private String toTel; // 전화번호
	private String toHomePage; // 홈페이지
	private String toOverView; // 콘텐츠 개요 (CLOB이므로 String으로 처리)
	private String toImgPath; // 이미지 경로
	private String toX; // X좌표
	private String toY; // Y좌표

	public TourDTO() {
	}

	public TourDTO(String toId, String areacode, String tourType, String toName, String toAddress,
			String toDetailAddress, String toTime, String toTel, String toHomePage, String toOverView, String toImgPath,
			String toX, String toY) {
		this.toId = toId;
		this.areacode = areacode;
		this.tourType = tourType;
		this.toName = toName;
		this.toAddress = toAddress;
		this.toDetailAddress = toDetailAddress;
		this.toTime = toTime;
		this.toTel = toTel;
		this.toHomePage = toHomePage;
		this.toOverView = toOverView;
		this.toImgPath = toImgPath;
		this.toX = toX;
		this.toY = toY;
	}

	public String getToId() {
		return toId;
	}

	public String getAreacode() {
		return areacode;
	}

	public String getTourType() {
		return tourType;
	}

	public String getToName() {
		return toName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public String getToDetailAddress() {
		return toDetailAddress;
	}

	public String getToTime() {
		return toTime;
	}

	public String getToTel() {
		return toTel;
	}

	public String getToHomePage() {
		return toHomePage;
	}

	public String getToOverView() {
		return toOverView;
	}

	public String getToImgPath() {
		return toImgPath;
	}

	public String getToX() {
		return toX;
	}

	public String getToY() {
		return toY;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public void setTourType(String tourType) {
		this.tourType = tourType;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public void setToDetailAddress(String toDetailAddress) {
		this.toDetailAddress = toDetailAddress;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public void setToTel(String toTel) {
		this.toTel = toTel;
	}

	public void setToHomePage(String toHomePage) {
		this.toHomePage = toHomePage;
	}

	public void setToOverView(String toOverView) {
		this.toOverView = toOverView;
	}

	public void setToImgPath(String toImgPath) {
		this.toImgPath = toImgPath;
	}

	public void setToX(String toX) {
		this.toX = toX;
	}

	public void setToY(String toY) {
		this.toY = toY;
	}

	@Override
	public String toString() {
		return "TourDTO [toId=" + toId + ", areacode=" + areacode + ", tourType=" + tourType + ", toName=" + toName
				+ ", toAddress=" + toAddress + ", toDetailAddress=" + toDetailAddress + ", toTime=" + toTime
				+ ", toTel=" + toTel + ", toHomePage=" + toHomePage + ", toOverView=" + toOverView + ", toImgPath="
				+ toImgPath + ", toX=" + toX + ", toY=" + toY + "]";
	}

}
