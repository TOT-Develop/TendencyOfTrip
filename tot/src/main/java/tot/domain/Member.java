package tot.domain;

import java.util.Date;

public class Member {
	private String MEMID;
	private String MEMBER_001;
	private String MEMBER_002;
	private String MEMBER_003;
	private String MEMNICK;
	private Date MEMTEGDATE;
	private int TTIMG;
	
	public Member() {
	}

	public Member(String mEMID, String mEMBER_001, String mEMBER_002, String mEMBER_003, String mEMNICK,
			Date mEMTEGDATE, int tTIMG) {
		super();
		MEMID = mEMID;
		MEMBER_001 = mEMBER_001;
		MEMBER_002 = mEMBER_002;
		MEMBER_003 = mEMBER_003;
		MEMNICK = mEMNICK;
		MEMTEGDATE = mEMTEGDATE;
		TTIMG = tTIMG;
	}

	public String getMEMID() {
		return MEMID;
	}

	public void setMEMID(String mEMID) {
		MEMID = mEMID;
	}

	public String getMEMBER_001() {
		return MEMBER_001;
	}

	public void setMEMBER_001(String mEMBER_001) {
		MEMBER_001 = mEMBER_001;
	}

	public String getMEMBER_002() {
		return MEMBER_002;
	}

	public void setMEMBER_002(String mEMBER_002) {
		MEMBER_002 = mEMBER_002;
	}

	public String getMEMBER_003() {
		return MEMBER_003;
	}

	public void setMEMBER_003(String mEMBER_003) {
		MEMBER_003 = mEMBER_003;
	}

	public String getMEMNICK() {
		return MEMNICK;
	}

	public void setMEMNICK(String mEMNICK) {
		MEMNICK = mEMNICK;
	}

	public Date getMEMTEGDATE() {
		return MEMTEGDATE;
	}

	public void setMEMTEGDATE(Date mEMTEGDATE) {
		MEMTEGDATE = mEMTEGDATE;
	}

	public int getTTIMG() {
		return TTIMG;
	}

	public void setTTIMG(int tTIMG) {
		TTIMG = tTIMG;
	}

	@Override
	public String toString() {
		return "Member [MEMID=" + MEMID + ", MEMBER_001=" + MEMBER_001 + ", MEMBER_002=" + MEMBER_002 + ", MEMBER_003="
				+ MEMBER_003 + ", MEMNICK=" + MEMNICK + ", MEMTEGDATE=" + MEMTEGDATE + ", TTIMG=" + TTIMG + "]";
	}
	
	
	
}
