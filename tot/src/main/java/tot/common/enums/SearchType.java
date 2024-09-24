package tot.common.enums;

public enum SearchType {
	ALL("전체"), TITLE("제목"), CONTENT("내용");

	private final String description;

	SearchType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
