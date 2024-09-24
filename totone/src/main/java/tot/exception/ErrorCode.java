package tot.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	NOT_FOUND_TREVTITLE(HttpStatus.BAD_REQUEST, "PR001", "제목을 입력해주세요."),
	NOT_FOUND_TREVCOURSE(HttpStatus.BAD_REQUEST, "PR002", "코스를 선택해주세요."),
    NOT_FOUND_TREVCONTENT(HttpStatus.BAD_REQUEST, "PR003", "내용을 입력해주세요."),
	TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "PR004", "제목은 200자를 초과할 수 없습니다."),
	CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "PR005", "내용은 1000자를 초과할 수 없습니다."),
	NOT_FOUND_TREVAGREE(HttpStatus.BAD_REQUEST, "PR006", "개인정보 수집 및 이용동의를 체크해주세요."),
	NOT_CHECK_TREVAGREE(HttpStatus.BAD_REQUEST, "PR007", "개인정보 수집 및 이용동의를 체크해주세요.");

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
