package tot.domain;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TripVO {

    private int tripId; // 여행 아이디
    private String memId; // 회원 아이디
    private String areacode; // 지역코드
    private int trAmt; // 여행 예상 비용
    private Date trStadate; // 여행 시작일
    private Date trEnddate; // 예상 도착일
    private String trPeriod; // 여행기간
    private int trPeople; // 여행 인원수
    private String trImgpath; // 여행 이미지 경로
    private String regionName; // 여행지역 이름
    private String regionImageUrl; // 지역 이미지 URL

    public TripVO() {
    }

    public TripVO(int tripId, String memId, String areacode, int trAmt, Date trStadate, Date trEnddate, String trPeriod,
                  int trPeople, String trImgpath, String regionName, String regionImageUrl) {
        this.tripId = tripId;
        this.memId = memId;
        this.areacode = areacode;
        this.trAmt = trAmt;
        this.trStadate = trStadate;
        this.trEnddate = trEnddate;
        this.trPeriod = trPeriod;
        this.trPeople = trPeople;
        this.trImgpath = trImgpath;
        this.regionName = regionName;
        this.regionImageUrl = regionImageUrl;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public int getTrAmt() {
        return trAmt;
    }

    public void setTrAmt(int trAmt) {
        this.trAmt = trAmt;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getTrStadate() {
        return trStadate;
    }

    public void setTrStadate(Date trStadate) {
        this.trStadate = trStadate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getTrEnddate() {
        return trEnddate;
    }

    public void setTrEnddate(Date trEnddate) {
        this.trEnddate = trEnddate;
    }

    public String getTrPeriod() {
        return trPeriod;
    }

    public void setTrPeriod(String trPeriod) {
        this.trPeriod = trPeriod;
    }

    public int getTrPeople() {
        return trPeople;
    }

    public void setTrPeople(int trPeople) {
        this.trPeople = trPeople;
    }

    public String getTrImgpath() {
        return trImgpath;
    }

    public void setTrImgpath(String trImgpath) {
        this.trImgpath = trImgpath;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionImageUrl() {
        return regionImageUrl;
    }

    public void setRegionImageUrl(String regionImageUrl) {
        this.regionImageUrl = regionImageUrl;
    }

    // 여행 기간을 표시하는 메서드
    @JsonGetter("displayPeriod")
    public String getDisplayPeriod() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (trStadate != null && trEnddate != null) {
            return sdf.format(trStadate) + " ~ " + sdf.format(trEnddate);
        } else if (trPeriod != null) {
            return trPeriod;
        }
        return "정보 없음";
    }

    // 여행 예상 비용을 원화 단위로 포맷팅하는 메서드
    @JsonGetter("formattedTrAmt")
    public String getFormattedTrAmt() {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(trAmt) + " 원";
    }

    @Override
    public String toString() {
        return "TripVO [tripId=" + tripId + ", memId=" + memId + ", areacode=" + areacode + ", trAmt=" + trAmt
                + ", trStadate=" + trStadate + ", trEnddate=" + trEnddate + ", trPeriod=" + trPeriod + ", trPeople="
                + trPeople + ", trImgpath=" + trImgpath + ", regionName=" + regionName + ", regionImageUrl="
                + regionImageUrl + "]";
    }
}
