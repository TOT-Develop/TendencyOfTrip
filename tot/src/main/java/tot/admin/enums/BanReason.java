package tot.admin.enums;

public enum BanReason {
	SPAM("스팸"), INAPPROPRIATE("부적절한 내용"), HARASSMENT("괴롭힘"), COPYRIGHT("저작권 침해");

	private final String description;

	BanReason(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
