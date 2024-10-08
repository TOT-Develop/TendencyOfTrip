package tot.exception;

public class FileUploadInvalidFilenameException extends ServerException {

	public FileUploadInvalidFilenameException() {
		super(ErrorCode.FILE_UPLOAD_INVALID_FILENAME);
	}

}
