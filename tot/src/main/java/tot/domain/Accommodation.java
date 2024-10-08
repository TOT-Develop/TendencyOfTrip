package tot.domain;

public class Accommodation {
	private String lodName;
	private String lodAddress;
	private String lodURL;
	private String lodPrice;
	private String lodImgPath;
	private String lodLG;
	private String areacode;

	public Accommodation() {
	}

	public Accommodation(String lodName, String lodAddress, String lodURL, String lodPrice, String lodImgPath,
			String lodLG, String areacode) {
		this.lodName = lodName;
		this.lodAddress = lodAddress;
		this.lodURL = lodURL;
		this.lodPrice = lodPrice;
		this.lodImgPath = lodImgPath;
		this.lodLG = lodLG;
		this.areacode = areacode;
	}

	public String getLodName() {
		return lodName;
	}

	public void setLodName(String lodName) {
		this.lodName = lodName;
	}

	public String getLodAddress() {
		return lodAddress;
	}

	public void setLodAddress(String lodAddress) {
		this.lodAddress = lodAddress;
	}

	public String getLodURL() {
		return lodURL;
	}

	public void setLodURL(String lodURL) {
		this.lodURL = lodURL;
	}

	public String getLodPrice() {
		return lodPrice;
	}

	public void setLodPrice(String lodPrice) {
		this.lodPrice = lodPrice;
	}

	public String getLodImgPath() {
		return lodImgPath;
	}

	public void setLodImgPath(String lodImgPath) {
		this.lodImgPath = lodImgPath;
	}

	public String getLodLG() {
		return lodLG;
	}

	public void setLodLG(String lodLG) {
		this.lodLG = lodLG;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	@Override
	public String toString() {
		return "Accommodation [lodName=" + lodName + ", lodAddress=" + lodAddress + ", lodURL=" + lodURL + ", lodPrice="
				+ lodPrice + ", lodImgPath=" + lodImgPath + ", lodLG=" + lodLG + ", areacode=" + areacode + "]";
	}

}