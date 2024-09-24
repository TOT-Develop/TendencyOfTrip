package tot.domain;

import java.sql.Timestamp;


public class Qna {
	
	private int qnaid;
	private String memid;
	private String qna_001;
	private String qna_002;
	private String commentstatus;
	private String qnatitle;
	private String qnatext;
	private Timestamp qnaregdate;
	private Timestamp qnaupdate;
	
	public Qna() {
	}

	public Qna(int qnaid, String memid, String qna_001, String qna_002, String commentstatus, String qnatitle,
			String qnatext, Timestamp qnaregdate, Timestamp qnaupdate) {
		super();
		this.qnaid = qnaid;
		this.memid = memid;
		this.qna_001 = qna_001;
		this.qna_002 = qna_002;
		this.commentstatus = commentstatus;
		this.qnatitle = qnatitle;
		this.qnatext = qnatext;
		this.qnaregdate = qnaregdate;
		this.qnaupdate = qnaupdate;
	}

	public int getQnaid() {
		return qnaid;
	}

	public void setQnaid(int qnaid) {
		this.qnaid = qnaid;
	}

	public String getMemid() {
		return memid;
	}

	public void setMemid(String memid) {
		this.memid = memid;
	}

	public String getQna_001() {
		return qna_001;
	}

	public void setQna_001(String qna_001) {
		this.qna_001 = qna_001;
	}

	public String getQna_002() {
		return qna_002;
	}

	public void setQna_002(String qna_002) {
		this.qna_002 = qna_002;
	}

	public String getCommentstatus() {
		return commentstatus;
	}

	public void setCommentstatus(String commentstatus) {
		this.commentstatus = commentstatus;
	}

	public String getQnatitle() {
		return qnatitle;
	}

	public void setQnatitle(String qnatitle) {
		this.qnatitle = qnatitle;
	}

	public String getQnatext() {
		return qnatext;
	}

	public void setQnatext(String qnatext) {
		this.qnatext = qnatext;
	}

	public Timestamp getQnaregdate() {
		return qnaregdate;
	}

	public void setQnaregdate(Timestamp qnaregdate) {
		this.qnaregdate = qnaregdate;
	}

	public Timestamp getQnaupdate() {
		return qnaupdate;
	}

	public void setQnaupdate(Timestamp qnaupdate) {
		this.qnaupdate = qnaupdate;
	}

	@Override
	public String toString() {
		return "Qna [qnaid=" + qnaid + ", memid=" + memid + ", qna_001=" + qna_001 + ", qna_002=" + qna_002
				+ ", commentstatus=" + commentstatus + ", qnatitle=" + qnatitle + ", qnatext=" + qnatext
				+ ", qnaregdate=" + qnaregdate + ", qnaupdate=" + qnaupdate + "]";
	}

	
	
	
	
	
}
