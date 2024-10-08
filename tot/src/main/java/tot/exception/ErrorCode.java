package tot.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// 회원 정보 관련 오류
	NOT_FOUND_MEMBERID(HttpStatus.UNAUTHORIZED, "AUTH01", "로그인이 필요합니다."),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "AUTH02", "사용자가 인증되지 않았습니다."),
	FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "AUTH03", "귀하의 계정은 서비스 이용이 불가합니다. 제재 관련 정보를 확인해 주세요."),
	DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "AUTH04", "중복된 닉네임입니다. 다시 입력해주세요."),

	// 서버 관련 오류
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SRV01", "서버 내부에서 오류가 발생했습니다."),
	SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "SRV02", "서비스를 사용할 수 없습니다. 잠시 후 다시 시도해 주세요."),
	SERVER_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "SRV03", "서버 응답이 지연되고 있습니다."),

	// 데이터베이스 오류
	DATABASE_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB01", "데이터베이스 연결에 실패했습니다."),
	QUERY_EXECUTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DB02", "쿼리 실행 중 오류가 발생했습니다."),
	DUPLICATE_ENTRY(HttpStatus.CONFLICT, "DB03", "해당 데이터가 이미 존재합니다."),

	// 예기치 않은 오류
	UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNK01", "예기치 않은 오류가 발생했습니다. 관리자에게 문의하세요."),

	INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "ARG01", "유효하지 않은 인수입니다."),

	// 여행 후기 입력 데이터 관련 오류
	NOT_FOUND_TREVTITLE(HttpStatus.BAD_REQUEST, "PR01", "여행 후기 제목을 입력해주세요."),
	NOT_FOUND_TRIPID(HttpStatus.BAD_REQUEST, "PR02", "여행을 선택해주세요."),
	NOT_FOUND_TREVCONTENT(HttpStatus.BAD_REQUEST, "PR03", "여행 후기 내용을 입력해주세요."),
	TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "PR04", "여행 후기 제목은 50자를 초과할 수 없습니다."),
	CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "PR05", "여행 후기 내용은 4000자를 초과할 수 없습니다."),
	NOT_FOUND_TREVAGREE(HttpStatus.BAD_REQUEST, "PR06", "개인정보 수집 및 이용에 대한 동의를 체크해주세요."),
	NOT_CHECK_TREVAGREE(HttpStatus.BAD_REQUEST, "PR07", "개인정보 수집 및 이용 동의를 체크해주세요."),
	NOT_CHECK_TREVRATING(HttpStatus.BAD_REQUEST, "PR08", "여행 후기 평점을 체크해주세요."),
	NOT_FOUND_REPORTREASON(HttpStatus.BAD_REQUEST, "PR09", "댓글 신고 시 사유를 작성해야 합니다."),

	// 공지사항 데이터 관련 오류
	DATABASE_CONNECTION_NOTICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "NO01", "공지사항 목록을 가져오는 중 데이터베이스 오류 발생"),

	// 문의사항 데이터 관련 오류
	DATABASE_CONNECTION_QNA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Q01", "문의사항 데이터 가져오는 중 데이터베이스 오류 발생"),
	NOT_FOUND_QNATITLE(HttpStatus.BAD_REQUEST, "Q02", "문의글 제목을 입력해주세요."),
	QNATITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "Q03", "문의글 제목은 50자를 초과할 수 없습니다."),
	NOT_FOUND_QNATYPEID(HttpStatus.BAD_REQUEST, "Q04", "문의유형을 선택해주세요."),
	NOT_FOUND_QNACONTENT(HttpStatus.BAD_REQUEST, "Q05", "문의글 내용을 입력해주세요."),
	QNACONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "Q06", "문의글 내용은 4000자를 초과할 수 없습니다."),
	NOT_CHECK_QNAAGREE(HttpStatus.BAD_REQUEST, "Q07", "개인정보 수집 및 이용 동의를 체크해주세요."),

	// 파일 업로드 관련 오류
	FILE_UPLOAD_DIRECTORY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "FILE01", "파일 업로드 디렉토리 설정이 필요합니다."),
	FILE_UPLOAD_INVALID_FILENAME(HttpStatus.BAD_REQUEST, "FILE02", "유효하지 않은 파일 이름입니다."),
	FILE_UPLOAD_SIZE_TOO_LONG(HttpStatus.PAYLOAD_TOO_LARGE, "FILE03", "파일 크기가 10MB를 초과할 수 없습니다."),
	FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE04", "파일 저장 중 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
