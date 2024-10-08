package tot.exception;

public class QueryExecutionFailedException extends ServerException {

	public QueryExecutionFailedException() {
		super(ErrorCode.QUERY_EXECUTION_FAILED);
	}

}
