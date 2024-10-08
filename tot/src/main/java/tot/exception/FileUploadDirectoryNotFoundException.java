package tot.exception;

public class FileUploadDirectoryNotFoundException extends ServerException {

	public FileUploadDirectoryNotFoundException() {
		super(ErrorCode.FILE_UPLOAD_DIRECTORY_NOT_FOUND);
	}

}
