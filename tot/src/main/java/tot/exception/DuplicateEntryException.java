package tot.exception;

public class DuplicateEntryException extends ServerException {

	public DuplicateEntryException() {
		super(ErrorCode.DUPLICATE_ENTRY);
	}

}
