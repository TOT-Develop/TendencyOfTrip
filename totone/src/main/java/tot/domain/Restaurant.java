package tot.domain;

public class Restaurant { 
	private int RESTID;
	private String REGION_001;
	private String RESTNAME;
	private String RESTADDRESS;
	private String RESTAURANT_001;
	private String RESTX;
	private String RESTY;
	
	public Restaurant() {
	}

	public Restaurant(int rESTID, String rEGION_001, String rESTNAME, String rESTADDRESS, String rESTAURANT_001,
			String rESTX, String rESTY) {
		super();
		RESTID = rESTID;
		REGION_001 = rEGION_001;
		RESTNAME = rESTNAME;
		RESTADDRESS = rESTADDRESS;
		RESTAURANT_001 = rESTAURANT_001;
		RESTX = rESTX;
		RESTY = rESTY;
	}

	public int getRESTID() {
		return RESTID;
	}

	public void setRESTID(int rESTID) {
		RESTID = rESTID;
	}

	public String getREGION_001() {
		return REGION_001;
	}

	public void setREGION_001(String rEGION_001) {
		REGION_001 = rEGION_001;
	}

	public String getRESTNAME() {
		return RESTNAME;
	}

	public void setRESTNAME(String rESTNAME) {
		RESTNAME = rESTNAME;
	}

	public String getRESTADDRESS() {
		return RESTADDRESS;
	}

	public void setRESTADDRESS(String rESTADDRESS) {
		RESTADDRESS = rESTADDRESS;
	}

	public String getRESTAURANT_001() {
		return RESTAURANT_001;
	}

	public void setRESTAURANT_001(String rESTAURANT_001) {
		RESTAURANT_001 = rESTAURANT_001;
	}

	public String getRESTX() {
		return RESTX;
	}

	public void setRESTX(String rESTX) {
		RESTX = rESTX;
	}

	public String getRESTY() {
		return RESTY;
	}

	public void setRESTY(String rESTY) {
		RESTY = rESTY;
	}

	@Override
	public String toString() {
		return "Restaurant [RESTID=" + RESTID + ", REGION_001=" + REGION_001 + ", RESTNAME=" + RESTNAME
				+ ", RESTADDRESS=" + RESTADDRESS + ", RESTAURANT_001=" + RESTAURANT_001 + ", RESTX=" + RESTX
				+ ", RESTY=" + RESTY + "]";
	}

	
	
	
	

	
	
	
}
