package tot.exception;

public class FileUploadSizeTooLongException extends ValidationException {

	public FileUploadSizeTooLongException() {
		super(ErrorCode.FILE_UPLOAD_SIZE_TOO_LONG);
	}

}