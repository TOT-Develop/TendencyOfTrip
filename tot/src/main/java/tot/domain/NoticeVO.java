package tot.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NoticeVO {

	// 날짜 포맷 지정
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	private int noId; // 공지사항 아이디
	private String noTitle; // 공지사항 제목
	private String noText; // 공지사항 내용
	private Timestamp noRegdate; // 등록일자
	private Timestamp noUpdate; // 수정일자

	public NoticeVO() {
	}

	public NoticeVO(int noId, String noTitle, String noText, Timestamp noRegdate, Timestamp noUpdate) {
		this.noId = noId;
		this.noTitle = noTitle;
		this.noText = noText;
		this.noRegdate = noRegdate;
		this.noUpdate = noUpdate;
	}

	public int getNoId() {
		return noId;
	}

	public String getNoTitle() {
		return noTitle;
	}

	public String getNoText() {
		return noText;
	}

	public Timestamp getNoRegdate() {
		return noRegdate;
	}

	public Timestamp getNoUpdate() {
		return noUpdate;
	}

	// 등록일자에 대한 포맷된 문자열 반환
	public String getFormattedNoregdate() {
		if (noRegdate != null) {
			return dateFormat.format(noRegdate);
		}
		return null;
	}

	// 수정일자에 대한 포맷된 문자열 반환
	public String getFormattedNoupdate() {
		if (noUpdate != null) {
			return dateFormat.format(noUpdate);
		}
		return null;
	}

	@Override
	public String toString() {
		return "NoticeVO [noId=" + noId + ", noTitle=" + noTitle + ", noText=" + noText + ", noRegdate="
				+ getFormattedNoregdate() + ", noUpdate=" + getFormattedNoupdate() + "]";
	}

} // class
