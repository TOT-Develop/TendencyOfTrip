package tot.domain;

import java.sql.Timestamp;

public class MemberVO {
	private String memId; // 회원 아이디
	private String memNick; // 회원 닉네임
	private String memEmail; // 회원 이메일
	private String member_status; // member_status_회원상태(M01-정상 / M02-제제 / M03-정지)
	private String member_mbti; // member_mbti_MBTI 유형
	private String member_tt; // member_tt_여행성향유형(TT01 ~ TT07)
	private Timestamp memRegDate; // 회원 등록일
	private Timestamp memUpdate; // 회원 수정일
	private String ttImg; // 프로필 이미지
	private Timestamp memberBanStart; // 정지 시작일
	private Timestamp memberBanEnd; // 정지 종료일

	public MemberVO() {
	}

	public MemberVO(String memId, String memNick, String memEmail, String member_status, String member_mbti,
			String member_tt, Timestamp memRegDate, Timestamp memUpdate, String ttImg, Timestamp memberBanStart,
			Timestamp memberBanEnd) {
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

	public MemberVO(String memId, String member_status) {
		this.memId = memId;
		this.member_status = member_status;
	}

	public MemberVO(String memId, String memNick, String memEmail, Timestamp memRegDate) {
		this.memId = memId;
		this.memNick = memNick;
		this.memEmail = memEmail;
		this.memRegDate = memRegDate;
	}

	public void changeMemnick(String memNick) {
		this.memNick = memNick;
	}

	public boolean isEmpty() {
		return memId == null && memNick == null && memEmail == null && member_status == null && member_mbti == null
				&& member_tt == null && memRegDate == null && memUpdate == null && ttImg == null
				&& memberBanStart == null && memberBanEnd == null;
	}

	public String getMemId() {
		return memId;
	}

	public String getMemNick() {
		return memNick;
	}

	public String getMemEmail() {
		return memEmail;
	}

	public String getMember_status() {
		return member_status;
	}

	public String getMember_mbti() {
		return member_mbti;
	}

	public String getMember_tt() {
		return member_tt;
	}

	public Timestamp getMemRegDate() {
		return memRegDate;
	}

	public Timestamp getMemUpdate() {
		return memUpdate;
	}

	public String getTtImg() {
		return ttImg;
	}

	public Timestamp getMemberBanStart() {
		return memberBanStart;
	}

	public Timestamp getMemberBanEnd() {
		return memberBanEnd;
	}

	@Override
	public String toString() {
		return "MemberVO [memId=" + memId + ", memNick=" + memNick + ", memEmail=" + memEmail + ", member_status="
				+ member_status + ", member_mbti=" + member_mbti + ", member_tt=" + member_tt + ", memRegDate="
				+ memRegDate + ", memUpdate=" + memUpdate + ", ttImg=" + ttImg + ", memberBanStart=" + memberBanStart
				+ ", memberBanEnd=" + memberBanEnd + "]";
	}

}
