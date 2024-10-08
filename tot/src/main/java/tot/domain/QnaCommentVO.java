package tot.domain;

import java.sql.Timestamp;

public class QnaCommentVO {

	private int qnacId;
	private int qnaId;
	private String qnacText;
	private Timestamp qnacRegdate;
	private Timestamp qnacUpdate;

	public QnaCommentVO() {
	}

	public QnaCommentVO(int qnaId, String qnacText, Timestamp qnacRegdate, Timestamp qnacUpdate) {
		this.qnaId = qnaId;
		this.qnacText = qnacText;
		this.qnacRegdate = qnacRegdate;
		this.qnacUpdate = qnacUpdate;
	}

	public int getQnacId() {
		return qnacId;
	}

	public int getQnaId() {
		return qnaId;
	}

	public String getQnacText() {
		return qnacText;
	}

	public Timestamp getQnacRegdate() {
		return qnacRegdate;
	}

	public Timestamp getQnacUpdate() {
		return qnacUpdate;
	}

	@Override
	public String toString() {
		return "QnaCommentVO [qnacId=" + qnacId + ", qnaId=" + qnaId + ", qnacText=" + qnacText + ", qnacRegdate="
				+ qnacRegdate + ", qnacUpdate=" + qnacUpdate + "]";
	}

}
