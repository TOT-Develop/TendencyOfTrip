package tot.domain;

import java.sql.Timestamp;

import tot.common.enums.Flag;
import tot.common.enums.ProcessStatus;

public class QnaVO {

	private int qnaId;
	private String memId;
	private String qna_001;
	private ProcessStatus qna_002;
	private Flag commentStatus;
	private String qnaTitle;
	private String qnaText;
	private Timestamp qnaRegdate;
	private Timestamp qnaUpdate;

	public QnaVO() {
	}

	public static QnaVO fromDTO(QnaDTO dto, MemberVO member) {
		return new QnaVO(0, member.getMemId(), dto.getQna_001(), ProcessStatus.RECEIVED, Flag.CMT001, dto.getQnaTitle(),
				dto.getQnaText(), null, null);
	}

	public QnaVO(int qnaId, String memId, String qna_001, ProcessStatus qna_002, Flag commentStatus, String qnaTitle,
			String qnaText, Timestamp qnaRegdate, Timestamp qnaUpdate) {
		this.qnaId = qnaId;
		this.memId = memId;
		this.qna_001 = qna_001;
		this.qna_002 = qna_002;
		this.commentStatus = commentStatus;
		this.qnaTitle = qnaTitle;
		this.qnaText = qnaText;
		this.qnaRegdate = qnaRegdate;
		this.qnaUpdate = qnaUpdate;
	}

	public int getQnaId() {
		return qnaId;
	}

	public String getMemId() {
		return memId;
	}

	public String getQna_001() {
		return qna_001;
	}

	public ProcessStatus getQna_002() {
		return qna_002;
	}

	public Flag getCommentStatus() {
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

	@Override
	public String toString() {
		return "QnaVO [qnaId=" + qnaId + ", memId=" + memId + ", qna_001=" + qna_001 + ", qna_002=" + qna_002
				+ ", commentStatus=" + commentStatus + ", qnaTitle=" + qnaTitle + ", qnaText=" + qnaText
				+ ", qnaRegdate=" + qnaRegdate + ", qnaUpdate=" + qnaUpdate + "]";
	}

}