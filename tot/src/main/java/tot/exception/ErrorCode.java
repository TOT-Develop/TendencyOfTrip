package tot.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	NOT_FOUND_MEMBERID(HttpStatus.FORBIDDEN, "M01", "회원 정보를 찾을 수 없습니다."),
	NOT_FOUND_TREVTITLE(HttpStatus.BAD_REQUEST, "PR001", "제목을 입력해주세요."),
	NOT_FOUND_TRIPID(HttpStatus.BAD_REQUEST, "PR002", "여행을 선택해주세요."),
    NOT_FOUND_TREVCONTENT(HttpStatus.BAD_REQUEST, "PR003", "내용을 입력해주세요."),
	TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "PR004", "제목은 200자를 초과할 수 없습니다."),
	CONTENT_TOO_LONG(HttpStatus.BAD_REQUEST, "PR005", "내용은 1000자를 초과할 수 없습니다."),
	NOT_FOUND_TREVAGREE(HttpStatus.BAD_REQUEST, "PR006", "개인정보 수집 및 이용동의를 체크해주세요."),
	NOT_CHECK_TREVAGREE(HttpStatus.BAD_REQUEST, "PR007", "개인정보 수집 및 이용동의를 체크해주세요."),
	NOT_CHECK_TREVRATING(HttpStatus.BAD_REQUEST, "PR008", "여행 후기 평점을 체크해주세요."),
	DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "PR009", "중복된 닉네임입니다. 다시 입력해주세요."),
	INVALID_NICKNAME_FORMAT(HttpStatus.BAD_REQUEST, "PR010","닉네임은 2~10자의 한글, 영문, 숫자만 사용할 수 있습니다.");

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
