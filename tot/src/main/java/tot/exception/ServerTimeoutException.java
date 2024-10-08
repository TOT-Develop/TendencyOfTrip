package tot.exception;

public class ServerTimeoutException extends ServerException {

	public ServerTimeoutException() {
		super(ErrorCode.SERVER_TIMEOUT);
	}

}
