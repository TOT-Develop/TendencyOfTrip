package tot.common.enums;

public enum SortType {
	LATEST("최신순"), OLDEST("오래된순"), VIEWS("조회순"), RATING("평점순");

	private final String description;

	SortType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
