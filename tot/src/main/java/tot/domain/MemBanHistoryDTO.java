package tot.domain;

import java.sql.Timestamp;

public class MemBanHistoryDTO {
	    private int memBanId;         // 제재 고유번호
	    private String memId;         // 회원 아이디
	    private String banReason;     // 제재 사유
	    private Timestamp banStart;   // 제재 시작 일시
	    private Timestamp banEnd;     // 제재 종료 일시
	    private Timestamp banLifted;  // 제재 해제 일시
	    private String liftReason;     // 해제 사유

	    public MemBanHistoryDTO() {
			// TODO Auto-generated constructor stub
		}

		public int getMemBanId() {
			return memBanId;
		}

		public void setMemBanId(int memBanId) {
			this.memBanId = memBanId;
		}

		public String getMemId() {
			return memId;
		}

		public void setMemId(String memId) {
			this.memId = memId;
		}

		public String getBanReason() {
			return banReason;
		}

		public void setBanReason(String banReason) {
			this.banReason = banReason;
		}

		public Timestamp getBanStart() {
			return banStart;
		}

		public void setBanStart(Timestamp banStart) {
			this.banStart = banStart;
		}

		public Timestamp getBanEnd() {
			return banEnd;
		}

		public void setBanEnd(Timestamp banEnd) {
			this.banEnd = banEnd;
		}

		public Timestamp getBanLifted() {
			return banLifted;
		}

		public void setBanLifted(Timestamp banLifted) {
			this.banLifted = banLifted;
		}

		public String getLiftReason() {
			return liftReason;
		}

		public void setLiftReason(String liftReason) {
			this.liftReason = liftReason;
		}

		public MemBanHistoryDTO(int memBanId, String memId, String banReason, Timestamp banStart, Timestamp banEnd,
				Timestamp banLifted, String liftReason) {
			super();
			this.memBanId = memBanId;
			this.memId = memId;
			this.banReason = banReason;
			this.banStart = banStart;
			this.banEnd = banEnd;
			this.banLifted = banLifted;
			this.liftReason = liftReason;
		}

		@Override
		public String toString() {
			return "MemBanHistoryDTO [memBanId=" + memBanId + ", memId=" + memId + ", banReason=" + banReason
					+ ", banStart=" + banStart + ", banEnd=" + banEnd + ", banLifted=" + banLifted + ", liftReason="
					+ liftReason + "]";
		}
	    
	    
	    
}
