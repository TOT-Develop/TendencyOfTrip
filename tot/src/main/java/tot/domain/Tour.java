package tot.domain;

public class Tour {

	private int toId;
	private String areacode;
	private String toName;
	private String toAddress;
	private String tourType;

	public Tour() {
	}

	public Tour(int toId, String areacode, String toName, String toAddress, String tourType) {
		this.toId = toId;
		this.areacode = areacode;
		this.toName = toName;
		this.toAddress = toAddress;
		this.tourType = tourType;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getTourType() {
		return tourType;
	}

	public void setTourType(String tourType) {
		this.tourType = tourType;
	}

	@Override
	public String toString() {
		return "Tour [toId=" + toId + ", areacode=" + areacode + ", toName=" + toName + ", toAddress=" + toAddress
				+ ", tourType=" + tourType + "]";
	}

}
