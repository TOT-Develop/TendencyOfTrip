package tot.exception;

public class ServiceUnavailableException extends ServerException {

	public ServiceUnavailableException() {
		super(ErrorCode.SERVICE_UNAVAILABLE);
	}

}
