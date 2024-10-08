package tot.exception;

public class UnexpectedErrorException extends ServerException {

	public UnexpectedErrorException() {
		super(ErrorCode.UNEXPECTED_ERROR);
	}

}
