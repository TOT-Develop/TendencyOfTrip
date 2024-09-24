package tot.domain;

import java.sql.Timestamp;

public class Notice {
	
	private int noid;				// 게시판 ID_SEQ_NOID
	private String notitle;			// 게시판 제목
	private String notext;			// 게시판 내용
	private Timestamp noregdate;	// 등록일자
	private Timestamp noupdate;		// 수정일자
	
	
	public Notice() {
	}
	
	public Notice(int noid, String notitle, String notext, Timestamp noregdate, Timestamp noupdate) {
		super();
		this.noid = noid;
		this.notitle = notitle;
		this.notext = notext;
		this.noregdate = noregdate;
		this.noupdate = noupdate;
	}

	public int getNoid() {
		return noid;
	}

	public void setNoid(int noid) {
		this.noid = noid;
	}

	public String getNotitle() {
		return notitle;
	}

	public void setNotitle(String notitle) {
		this.notitle = notitle;
	}

	public String getNotext() {
		return notext;
	}

	public void setNotext(String notext) {
		this.notext = notext;
	}

	public Timestamp getNoregdate() {
		return noregdate;
	}

	public void setNoregdate(Timestamp noregdate) {
		this.noregdate = noregdate;
	}

	public Timestamp getNoupdate() {
		return noupdate;
	}

	public void setNoupdate(Timestamp noupdate) {
		this.noupdate = noupdate;
	}

	@Override
	public String toString() {
		return "Notice [noid=" + noid + ", notitle=" + notitle + ", notext=" + notext + ", noregdate=" + noregdate
				+ ", noupdate=" + noupdate + "]";
	}
	
} // class
