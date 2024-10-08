package tot.domain;

import java.sql.Timestamp;
import java.util.List;

import tot.common.enums.Flag;

public class TReviewResDTO {

   private int trevId; // 여행후기 게시글 아이디
   private int tripId; // 여행 아이디
   private String memId; // 회원 아이디
   private String memNick; // 회원 닉네임
   private Flag trevStatus; // 게시여부코드
   private String trevTitle; // 후기 제목
   private String trevCourse; // 코스
   private String trevContent; // 내용
   private String trevRating; // 평점
   private Timestamp trevRegdate; // 작성일시
   private Timestamp trevUpdate; // 수정일시
   private String trevCount; // 조회수
   private List<TReviewImageVO> trevImages; // 이미지 경로

   public TReviewResDTO() {
   }

   public TReviewResDTO(int trevId, int tripId, String memId, String memNick, Flag trevStatus, String trevTitle,
         String trevCourse, String trevContent, String trevRating, Timestamp trevRegdate, Timestamp trevUpdate,
         String trevCount, List<TReviewImageVO> trevImages) {
      this.trevId = trevId;
      this.tripId = tripId;
      this.memId = memId;
      this.memNick = memNick;
      this.trevStatus = trevStatus;
      this.trevTitle = trevTitle;
      this.trevCourse = trevCourse;
      this.trevContent = trevContent;
      this.trevRating = trevRating;
      this.trevRegdate = trevRegdate;
      this.trevUpdate = trevUpdate;
      this.trevCount = trevCount;
      this.trevImages = trevImages;
   }

   public int getTrevId() {
      return trevId;
   }

   public int getTripId() {
      return tripId;
   }

   public String getMemId() {
      return memId;
   }

   public String getMemNick() {
      return memNick;
   }

   public Flag getTrevStatus() {
      return trevStatus;
   }

   public String getTrevTitle() {
      return trevTitle;
   }

   public String getTrevCourse() {
      return trevCourse;
   }

   public String getTrevContent() {
      return trevContent;
   }

   public String getTrevRating() {
      return trevRating;
   }

   public Timestamp getTrevRegdate() {
      return trevRegdate;
   }

   public Timestamp getTrevUpdate() {
      return trevUpdate;
   }

   public String getTrevCount() {
      return trevCount;
   }

   public List<TReviewImageVO> getTrevImages() {
      return trevImages;
   }

   public void setTrevId(int trevId) {
      this.trevId = trevId;
   }

   public void setTripId(int tripId) {
      this.tripId = tripId;
   }

   public void setMemId(String memId) {
      this.memId = memId;
   }

   public void setMemNick(String memNick) {
      this.memNick = memNick;
   }

   public void setTrevStatus(Flag trevStatus) {
      this.trevStatus = trevStatus;
   }

   public void setTrevTitle(String trevTitle) {
      this.trevTitle = trevTitle;
   }

   public void setTrevCourse(String trevCourse) {
      this.trevCourse = trevCourse;
   }

   public void setTrevContent(String trevContent) {
      this.trevContent = trevContent;
   }

   public void setTrevRating(String trevRating) {
      this.trevRating = trevRating;
   }

   public void setTrevRegdate(Timestamp trevRegdate) {
      this.trevRegdate = trevRegdate;
   }

   public void setTrevUpdate(Timestamp trevUpdate) {
      this.trevUpdate = trevUpdate;
   }

   public void setTrevCount(String trevCount) {
      this.trevCount = trevCount;
   }

   public void setTrevImages(List<TReviewImageVO> trevImages) {
      this.trevImages = trevImages;
   }

   @Override
   public String toString() {
      return "TReviewResDTO [trevId=" + trevId + ", tripId=" + tripId + ", memId=" + memId + ", memNick=" + memNick
            + ", trevStatus=" + trevStatus + ", trevTitle=" + trevTitle + ", trevCourse=" + trevCourse
            + ", trevContent=" + trevContent + ", trevRating=" + trevRating + ", trevRegdate=" + trevRegdate
            + ", trevUpdate=" + trevUpdate + ", trevCount=" + trevCount + ", trevImages=" + trevImages + "]";
   }

}
