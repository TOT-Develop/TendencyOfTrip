package tot.exception;

public class FileUploadException extends ServerException {

	public FileUploadException() {
		super(ErrorCode.FILE_UPLOAD_ERROR);
	}

}
