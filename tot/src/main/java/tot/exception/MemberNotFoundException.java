package tot.exception;

public class MemberNotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	// 기본 생성자
	public MemberNotFoundException() {
		this(ErrorCode.NOT_FOUND_MEMBERID);
	}

	// ErrorCode를 인자로 받는 생성자
	public MemberNotFoundException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
