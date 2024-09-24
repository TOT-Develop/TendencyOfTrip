package tot.domain;

public class FestivalDTO {
	 private String contentid;
	 private String contenttypeid;
	 private String areacode;
	 private String title;
	 private String addr1;
	 private String addr2;
	 private String mapx;
	 private String mapy;
	 private String firstimage;
	 private String firstimage2;
	 private String eventstartdate;
	 private String eventenddate;
	 private String tel;
	 private String overviewYN;
	 private String playtime;
	 private String usetimefestival;
	 
	 public FestivalDTO() {
		// TODO Auto-generated constructor stub
	}

	public FestivalDTO(String contentid, String contenttypeid, String areacode, String title, String addr1,
			String addr2, String mapx, String mapy, String firstimage, String firstimage2, String eventstartdate,
			String eventenddate, String tel, String overviewYN, String playtime, String usetimefestival) {
		super();
		this.contentid = contentid;
		this.contenttypeid = contenttypeid;
		this.areacode = areacode;
		this.title = title;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.mapx = mapx;
		this.mapy = mapy;
		this.firstimage = firstimage;
		this.firstimage2 = firstimage2;
		this.eventstartdate = eventstartdate;
		this.eventenddate = eventenddate;
		this.tel = tel;
		this.overviewYN = overviewYN;
		this.playtime = playtime;
		this.usetimefestival = usetimefestival;
	}

	public String getContentid() {
		return contentid;
	}

	public String getContenttypeid() {
		return contenttypeid;
	}

	public String getAreacode() {
		return areacode;
	}

	public String getTitle() {
		return title;
	}

	public String getAddr1() {
		return addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public String getMapx() {
		return mapx;
	}

	public String getMapy() {
		return mapy;
	}

	public String getFirstimage() {
		return firstimage;
	}

	public String getFirstimage2() {
		return firstimage2;
	}

	public String getEventstartdate() {
		return eventstartdate;
	}

	public String getEventenddate() {
		return eventenddate;
	}

	public String getTel() {
		return tel;
	}

	public String getOverviewYN() {
		return overviewYN;
	}

	public String getPlaytime() {
		return playtime;
	}

	public String getUsetimefestival() {
		return usetimefestival;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public void setContenttypeid(String contenttypeid) {
		this.contenttypeid = contenttypeid;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public void setMapx(String mapx) {
		this.mapx = mapx;
	}

	public void setMapy(String mapy) {
		this.mapy = mapy;
	}

	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}

	public void setFirstimage2(String firstimage2) {
		this.firstimage2 = firstimage2;
	}

	public void setEventstartdate(String eventstartdate) {
		this.eventstartdate = eventstartdate;
	}

	public void setEventenddate(String eventenddate) {
		this.eventenddate = eventenddate;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setOverviewYN(String overviewYN) {
		this.overviewYN = overviewYN;
	}

	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}

	public void setUsetimefestival(String usetimefestival) {
		this.usetimefestival = usetimefestival;
	}

	@Override
	public String toString() {
		return "FestivalDTO [contentid=" + contentid + ", contenttypeid=" + contenttypeid + ", areacode=" + areacode
				+ ", title=" + title + ", addr1=" + addr1 + ", addr2=" + addr2 + ", mapx=" + mapx + ", mapy=" + mapy
				+ ", firstimage=" + firstimage + ", firstimage2=" + firstimage2 + ", eventstartdate=" + eventstartdate
				+ ", eventenddate=" + eventenddate + ", tel=" + tel + ", overviewYN=" + overviewYN + ", playtime="
				+ playtime + ", usetimefestival=" + usetimefestival + "]";
	}
	 
	
	 
}
