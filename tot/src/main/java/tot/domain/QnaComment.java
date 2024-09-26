package tot.domain;

import java.sql.Timestamp;

public class QnaComment {
	
	private int qnacid;
	private int qnaid;
	private String qnactext;
	private Timestamp qnacregdate;
	private Timestamp qnacupdate;
	
	public QnaComment() {		
	}

	public QnaComment(int qnacid, int qnaid, String qnactext, Timestamp qnacgredate, Timestamp qnacupdate) {
		super();
		this.qnacid = qnacid;
		this.qnaid = qnaid;
		this.qnactext = qnactext;
		this.qnacregdate = qnacregdate;
		this.qnacupdate = qnacupdate;
	}

	public int getQnacid() {
		return qnacid;
	}

	public void setQnacid(int qnacid) {
		this.qnacid = qnacid;
	}

	public int getQnaid() {
		return qnaid;
	}

	public void setQnaid(int qnaid) {
		this.qnaid = qnaid;
	}

	public String getQnactext() {
		return qnactext;
	}

	public void setQnactext(String qnactext) {
		this.qnactext = qnactext;
	}

	public Timestamp getQnacregdate() {
		return qnacregdate;
	}

	public void setQnacregdate(Timestamp qnacregdate) {
		this.qnacregdate = qnacregdate;
	}

	public Timestamp getQnacupdate() {
		return qnacupdate;
	}

	public void setQnacupdate(Timestamp qnacupdate) {
		this.qnacupdate = qnacupdate;
	}

	@Override
	public String toString() {
		return "QnaComment [qnacid=" + qnacid + ", qnaid=" + qnaid + ", qnactext=" + qnactext + ", qnacgredate="
				+ qnacregdate + ", qnacupdate=" + qnacupdate + "]";
	}

}
