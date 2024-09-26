package tot.domain;

import java.sql.Timestamp;

public class MemberVO {
	 	private String memId;      	// MEMID
	    private String memNick;    	// MEMNICK
	    private String memEmail;	// MEMEMAIL
	    private String member_status;  	// member_status_회원상태(M01-정상 / M02-제제 / M03-정지)
	    private String member_mbti;  	// member_mbti_MBTI 유형
	    private String member_tt;  	// member_tt_여행성향유형(TT01 ~ TT07)
	    private Timestamp memRegDate; // MEMREGDATE
	    private Timestamp memUpdate; //  업데이
	    private String ttImg;      	// TTIMG
	    private Timestamp memberBanStart;// 제제 시작일
	    private Timestamp memberBanEnd;	// 제제 완료일
	    

	    // 기본 생성자
	    public MemberVO() {}


		public MemberVO(String memId, String memNick, String memEmail, String member_status, String member_mbti,
				String member_tt, Timestamp memRegDate, Timestamp memUpdate, String ttImg, Timestamp memberBanStart,
				Timestamp memberBanEnd) {
			super();
			this.memId = memId;
			this.memNick = memNick;
			this.memEmail = memEmail;
			this.member_status = member_status;
			this.member_mbti = member_mbti;
			this.member_tt = member_tt;
			this.memRegDate = memRegDate;
			this.memUpdate = memUpdate;
			this.ttImg = ttImg;
			this.memberBanStart = memberBanStart;
			this.memberBanEnd = memberBanEnd;
		}

		
		
		public String getMemId() {
			return memId;
		}


		public void setMemId(String memId) {
			this.memId = memId;
		}


		public String getMemNick() {
			return memNick;
		}


		public void setMemNick(String memNick) {
			this.memNick = memNick;
		}


		public String getMemEmail() {
			return memEmail;
		}


		public void setMemEmail(String memEmail) {
			this.memEmail = memEmail;
		}


		public String getMember_status() {
			return member_status;
		}


		public void setMember_status(String member_status) {
			this.member_status = member_status;
		}


		public String getMember_mbti() {
			return member_mbti;
		}


		public void setMember_mbti(String member_mbti) {
			this.member_mbti = member_mbti;
		}


		public String getMember_tt() {
			return member_tt;
		}


		public void setMember_tt(String member_tt) {
			this.member_tt = member_tt;
		}


		public Timestamp getMemRegDate() {
			return memRegDate;
		}


		public void setMemRegDate(Timestamp memRegDate) {
			this.memRegDate = memRegDate;
		}


		public Timestamp getMemUpdate() {
			return memUpdate;
		}


		public void setMemUpdate(Timestamp memUpdate) {
			this.memUpdate = memUpdate;
		}


		public String getTtImg() {
			return ttImg;
		}


		public void setTtImg(String ttImg) {
			this.ttImg = ttImg;
		}


		public Timestamp getMemberBanStart() {
			return memberBanStart;
		}


		public void setMemberBanStart(Timestamp memberBanStart) {
			this.memberBanStart = memberBanStart;
		}


		public Timestamp getMemberBanEnd() {
			return memberBanEnd;
		}


		public void setMemberBanEnd(Timestamp memberBanEnd) {
			this.memberBanEnd = memberBanEnd;
		}


		@Override
		public String toString() {
			return "Member [memId=" + memId + ", memNick=" + memNick + ", memEmail=" + memEmail + ", member_status="
					+ member_status + ", member_mbti=" + member_mbti + ", member_tt=" + member_tt + ", memRegDate="
					+ memRegDate + ", memUpdate=" + memUpdate + ", ttImg=" + ttImg + ", memberBanStart=" + memberBanStart
					+ ", memberBanEnd=" + memberBanEnd + "]";
		}
	    
	    
	    

}
