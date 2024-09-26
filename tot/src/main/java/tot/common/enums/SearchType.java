package tot.common.enums;

public enum SearchType {
	ALL("전체"), TITLE("제목"), CONTENT("내용"), POST("게시물"), MEMBER("사용자"), COMMENT("댓글"), Q01("계정관리"), Q02("기술지원"), Q03("불만요청"), Q04("기타요청");

	private final String description;

	SearchType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
