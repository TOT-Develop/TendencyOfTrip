package tot.exception;

public class ValidationException extends RuntimeException {

	private final ErrorCode errorCode;

	public ValidationException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
