package tot.common.enums;

public enum Flag {
	CMT001("완료"), CMT002("제재");

	private final String description;

	Flag(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
