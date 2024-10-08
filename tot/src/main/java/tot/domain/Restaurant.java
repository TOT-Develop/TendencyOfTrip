package tot.domain;

public class Restaurant {

	private int restId;
	private String restName;
	private String restaurant_001;
	private String restAddress;
	private String areacode;
	private String region_001;

	public Restaurant() {
	}

	public int getRestId() {
		return restId;
	}

	public void setRestId(int restId) {
		this.restId = restId;
	}

	public String getRestName() {
		return restName;
	}

	public void setRestName(String restName) {
		this.restName = restName;
	}

	public String getRestaurant_001() {
		return restaurant_001;
	}

	public void setRestaurant_001(String restaurant_001) {
		this.restaurant_001 = restaurant_001;
	}

	public String getRestAddress() {
		return restAddress;
	}

	public void setRestAddress(String restAddress) {
		this.restAddress = restAddress;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getRegion_001() {
		return region_001;
	}

	public void setRegion_001(String region_001) {
		this.region_001 = region_001;
	}

	public Restaurant(int restId, String restName, String restaurant_001, String restAddress, String areacode,
			String region_001) {
		super();
		this.restId = restId;
		this.restName = restName;
		this.restaurant_001 = restaurant_001;
		this.restAddress = restAddress;
		this.areacode = areacode;
		this.region_001 = region_001;
	}

	@Override
	public String toString() {
		return "Restaurant [restId=" + restId + ", restName=" + restName + ", restaurant_001=" + restaurant_001
				+ ", restAddress=" + restAddress + ", areacode=" + areacode + ", region_001=" + region_001 + "]";
	}

}
