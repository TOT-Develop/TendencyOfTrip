package tot.domain;

public class RestaurantDTO {

	private int restId; // 레스토랑 아이디
	private String areacode; // 지역코드
	private String restaurant001; // 코드 (RESTAURANT_001)
	private String restName; // 레스토랑 이름
	private String restAddress; // 레스토랑 주소
	private String restX; // X 좌표
	private String restY; // Y 좌표

	public RestaurantDTO() {
		// TODO Auto-generated constructor stub
	}

	public RestaurantDTO(int restId, String areacode, String restaurant001, String restName, String restAddress,
			String restX, String restY) {
		super();
		this.restId = restId;
		this.areacode = areacode;
		this.restaurant001 = restaurant001;
		this.restName = restName;
		this.restAddress = restAddress;
		this.restX = restX;
		this.restY = restY;
	}

	public int getRestId() {
		return restId;
	}

	public String getAreacode() {
		return areacode;
	}

	public String getRestaurant001() {
		return restaurant001;
	}

	public String getRestName() {
		return restName;
	}

	public String getRestAddress() {
		return restAddress;
	}

	public String getRestX() {
		return restX;
	}

	public String getRestY() {
		return restY;
	}

	public void setRestId(int restId) {
		this.restId = restId;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public void setRestaurant001(String restaurant001) {
		this.restaurant001 = restaurant001;
	}

	public void setRestName(String restName) {
		this.restName = restName;
	}

	public void setRestAddress(String restAddress) {
		this.restAddress = restAddress;
	}

	public void setRestX(String restX) {
		this.restX = restX;
	}

	public void setRestY(String restY) {
		this.restY = restY;
	}

	@Override
	public String toString() {
		return "RestaurantDTO [restId=" + restId + ", areacode=" + areacode + ", restaurant001=" + restaurant001
				+ ", restName=" + restName + ", restAddress=" + restAddress + ", restX=" + restX + ", restY=" + restY
				+ "]";
	}
	
	

}
