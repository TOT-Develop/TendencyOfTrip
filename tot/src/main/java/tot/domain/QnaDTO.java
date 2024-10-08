package tot.domain;

import java.sql.Timestamp;

import tot.exception.ErrorCode;
import tot.util.ValidationUtil;

public class QnaDTO {

	private int qnaId;
	private String memId;
	private String memNick;
	private String qna_001;
	private String qna_002;
	private String commentStatus;
	private String qnaTitle;
	private String qnaText;
	private Timestamp qnaRegdate;
	private Timestamp qnaUpdate;
	private String qnaAgree;

	public QnaDTO() {
	}

	public QnaDTO(int qnaId, String memId, String memNick, String qna_001, String qna_002, String commentStatus,
			String qnaTitle, String qnaText, Timestamp qnaRegdate, Timestamp qnaUpdate, String qnaAgree) {
		this.qnaId = qnaId;
		this.memId = memId;
		this.memNick = memNick;
		this.qna_001 = qna_001;
		this.qna_002 = qna_002;
		this.commentStatus = commentStatus;
		this.qnaTitle = qnaTitle;
		this.qnaText = qnaText;
		this.qnaRegdate = qnaRegdate;
		this.qnaUpdate = qnaUpdate;
		this.qnaAgree = qnaAgree;
	}

	// 문의글 검증 메소드
	public void validate() {
		ValidationUtil.validateNotEmpty(qnaTitle, ErrorCode.NOT_FOUND_QNATITLE);
		ValidationUtil.validateLength(qnaTitle, 50, ErrorCode.QNATITLE_TOO_LONG);
		ValidationUtil.validateNotEmpty(qna_001, ErrorCode.NOT_FOUND_QNATYPEID);
		ValidationUtil.validateNotEmpty(qnaText, ErrorCode.NOT_FOUND_QNACONTENT);
		ValidationUtil.validateLength(qnaText, 4000, ErrorCode.QNACONTENT_TOO_LONG);
		ValidationUtil.validateNotEmpty(qnaAgree, ErrorCode.NOT_CHECK_TREVAGREE);
	}

	public int getQnaId() {
		return qnaId;
	}

	public String getMemId() {
		return memId;
	}

	public String getMemNick() {
		return memNick;
	}

	public String getQna_001() {
		return qna_001;
	}

	public String getQna_002() {
		return qna_002;
	}

	public String getCommentStatus() {
		return commentStatus;
	}

	public String getQnaTitle() {
		return qnaTitle;
	}

	public String getQnaText() {
		return qnaText;
	}

	public Timestamp getQnaRegdate() {
		return qnaRegdate;
	}

	public Timestamp getQnaUpdate() {
		return qnaUpdate;
	}

	public String getQnaAgree() {
		return qnaAgree;
	}

	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public void setMemNick(String memNick) {
		this.memNick = memNick;
	}

	public void setQna_001(String qna_001) {
		this.qna_001 = qna_001;
	}

	public void setQna_002(String qna_002) {
		this.qna_002 = qna_002;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}

	public void setQnaText(String qnaText) {
		this.qnaText = qnaText;
	}

	public void setQnaRegdate(Timestamp qnaRegdate) {
		this.qnaRegdate = qnaRegdate;
	}

	public void setQnaUpdate(Timestamp qnaUpdate) {
		this.qnaUpdate = qnaUpdate;
	}

	public void setQnaAgree(String qnaAgree) {
		this.qnaAgree = qnaAgree;
	}

	@Override
	public String toString() {
		return "QnaDTO [qnaId=" + qnaId + ", memId=" + memId + ", memNick=" + memNick + ", qna_001=" + qna_001
				+ ", qna_002=" + qna_002 + ", commentStatus=" + commentStatus + ", qnaTitle=" + qnaTitle + ", qnaText="
				+ qnaText + ", qnaRegdate=" + qnaRegdate + ", qnaUpdate=" + qnaUpdate + ", qnaAgree=" + qnaAgree + "]";
	}

}