package tot.exception;

public class InvalidMemberStatusException extends CustomException {

	public InvalidMemberStatusException() {
		super(ErrorCode.FORBIDDEN_ACCESS);
	}

}
