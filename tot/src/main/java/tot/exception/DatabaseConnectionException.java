package tot.exception;

public class DatabaseConnectionException extends ServerException {

	public DatabaseConnectionException() {
		super(ErrorCode.DATABASE_CONNECTION_ERROR);
	}

}
