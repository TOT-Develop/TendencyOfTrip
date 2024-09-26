package tot.common.enums;

public enum Flag {
	CMT001("완료"), CMT002("삭제"), CMT003("신고"), CMT004("제재"), CMT005("수정"),M01("정상회원"),M02("제재회원"),M03("탈퇴회원");

	private final String description;

	Flag(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
    public String toString() {
        return description; // toString()에서 description 반환
    }
	
}
