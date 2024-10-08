package tot.domain;

public class Hotel {

	private int lodId;
	private String lg_001;
	private String areacode;
	private String lodName;
	private String lodAddress;
	private String lodurl;
	private String lodPrice;
	private String lodimgpath;

	public Hotel() {
	}

	public Hotel(int lodId, String lg_001, String areacode, String lodName, String lodAddress, String lodurl,
			String lodPrice, String lodimgpath) {
		this.lodId = lodId;
		this.lg_001 = lg_001;
		this.areacode = areacode;
		this.lodName = lodName;
		this.lodAddress = lodAddress;
		this.lodurl = lodurl;
		this.lodPrice = lodPrice;
		this.lodimgpath = lodimgpath;
	}

	public int getLodId() {
		return lodId;
	}

	public void setLodId(int lodId) {
		this.lodId = lodId;
	}

	public String getLg_001() {
		return lg_001;
	}

	public void setLg_001(String lg_001) {
		this.lg_001 = lg_001;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
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

	public String getLodurl() {
		return lodurl;
	}

	public void setLodurl(String lodurl) {
		this.lodurl = lodurl;
	}

	public String getLodPrice() {
		return lodPrice;
	}

	public void setLodPrice(String lodPrice) {
		this.lodPrice = lodPrice;
	}

	public String getLodimgpath() {
		return lodimgpath;
	}

	public void setLodimgpath(String lodimgpath) {
		this.lodimgpath = lodimgpath;
	}

	@Override
	public String toString() {
		return "Hotel [lodId=" + lodId + ", lg_001=" + lg_001 + ", areacode=" + areacode + ", lodName=" + lodName
				+ ", lodAddress=" + lodAddress + ", lodurl=" + lodurl + ", lodPrice=" + lodPrice + ", lodimgpath="
				+ lodimgpath + "]";
	}

}
