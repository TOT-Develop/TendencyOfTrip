package tot.domain;

public class MemberTendency {
	private String memId;
	private String resultType;

	public MemberTendency() {
	}

	public MemberTendency(String memId, String resultType) {
		this.memId = memId;
		this.resultType = resultType;
	}

	public String getMemId() {
		return memId;
	}

	public String getResultType() {
		return resultType;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@Override
	public String toString() {
		return "MemberTendency [memId=" + memId + ", resultType=" + resultType + "]";
	}

}
