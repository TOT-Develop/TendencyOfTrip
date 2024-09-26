package tot.domain;

public class LodgingDTO {
	private int lodId;                // 숙소 아이디
	private String lg001;             // 코드 (LG_001)
	private String areacode;          // 지역코드
	private String lodName;           // 숙소 이름
	private String lodAddress;        // 숙소 주소
	private String lodUrl;            // 숙소 URL
	private String lodPrice;          // 숙소 가격
	private String lodImgPath;        // 숙소 이미지 경로
	
	public LodgingDTO() {
		// TODO Auto-generated constructor stub
	}

	public LodgingDTO(int lodId, String lg001, String areacode, String lodName, String lodAddress, String lodUrl,
			String lodPrice, String lodImgPath) {
		super();
		this.lodId = lodId;
		this.lg001 = lg001;
		this.areacode = areacode;
		this.lodName = lodName;
		this.lodAddress = lodAddress;
		this.lodUrl = lodUrl;
		this.lodPrice = lodPrice;
		this.lodImgPath = lodImgPath;
	}

	public int getLodId() {
		return lodId;
	}

	public String getLg001() {
		return lg001;
	}

	public String getAreacode() {
		return areacode;
	}

	public String getLodName() {
		return lodName;
	}

	public String getLodAddress() {
		return lodAddress;
	}

	public String getLodUrl() {
		return lodUrl;
	}

	public String getLodPrice() {
		return lodPrice;
	}

	public String getLodImgPath() {
		return lodImgPath;
	}

	public void setLodId(int lodId) {
		this.lodId = lodId;
	}

	public void setLg001(String lg001) {
		this.lg001 = lg001;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public void setLodName(String lodName) {
		this.lodName = lodName;
	}

	public void setLodAddress(String lodAddress) {
		this.lodAddress = lodAddress;
	}

	public void setLodUrl(String lodUrl) {
		this.lodUrl = lodUrl;
	}

	public void setLodPrice(String lodPrice) {
		this.lodPrice = lodPrice;
	}

	public void setLodImgPath(String lodImgPath) {
		this.lodImgPath = lodImgPath;
	}

	@Override
	public String toString() {
		return "LodgingDTO [lodId=" + lodId + ", lg001=" + lg001 + ", areacode=" + areacode + ", lodName=" + lodName
				+ ", lodAddress=" + lodAddress + ", lodUrl=" + lodUrl + ", lodPrice=" + lodPrice + ", lodImgPath="
				+ lodImgPath + "]";
	}
	
	
	
	
}
