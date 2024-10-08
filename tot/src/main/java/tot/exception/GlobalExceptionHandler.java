package tot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 사용자 예외 처리 메소드
	 *
	 * @param ex 발생한 사용자 예외
	 * @return 사용자 알림창 공통 페이지
	 */
	@ExceptionHandler(CustomException.class)
	public ModelAndView handleCustomException(CustomException ex) {
		logError(ex);

		return createModelAndView("common", ex.getMessage(), null);
	}

	/**
	 * 사용자 예외 처리 메소드
	 *
	 * @param ex 발생한 사용자 예외
	 * @return 사용자 알림창 공통 JSON 응답
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(ValidationException ex) {
		logError(ex);

		return createAlertResponse(ex.getErrorCode());
	}

	/**
	 * 사용자 예외 처리 메소드
	 *
	 * @param ex 발생한 사용자 예외
	 * @return 사용자 로그인 페이지
	 */
	@ExceptionHandler(MemberNotFoundException.class)
	public ModelAndView handleMemberNotFoundException(MemberNotFoundException ex) {
		logError(ex);

		return createModelAndView("common", ex.getMessage(), "login");
	}

	/**
	 * IllegalArgumentException 처리 메소드
	 *
	 * @param ex 발생한 잘못된 인자 예외
	 * @return 잘못된 인자에 대한 에러 페이지
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
		logError(ex);

		return createErrorResponse("errorPage", ErrorCode.INVALID_ARGUMENT);
	}

	/**
	 * CannotGetJdbcConnectionException 처리 메소드
	 *
	 * @param ex 발생한 JDBC 연결 예외
	 * @return 데이터베이스 연결 오류 페이지
	 */
	@ExceptionHandler(org.springframework.jdbc.CannotGetJdbcConnectionException.class)
	public ModelAndView handleCannotGetJdbcConnectionException(
			org.springframework.jdbc.CannotGetJdbcConnectionException ex) {
		logError(ex);

		return createErrorResponse("errorPage", ErrorCode.DATABASE_CONNECTION_ERROR);
	}

	/**
	 * 파일 최대 크기 초과로 인한 업로드 예외 처리 메소드
	 * 
	 * @param ex 발생한 MaxUploadSizeExceededException 예외
	 * @return 파일 크기 초과에 대한 커스텀 예외 FileUploadSizeTooLongException
	 */
	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
		logError(ex);

		ErrorResponse errorResponse = new ErrorResponse("FILE03", "파일 크기가 너무 큽니다.", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 서버 예외 처리 메소드
	 *
	 * @param ex 발생한 서버 예외
	 * @return 서버 에러 페이지
	 */
	@ExceptionHandler(ServerException.class)
	public ModelAndView handleGlobalException(ServerException ex) {
		logError(ex);

		return createErrorResponse("errorPage", ex.getErrorCode());
	}

	/**
	 * 사용자 예외 알림 생성 메소드
	 *
	 * @param errorCode 오류 코드
	 * @return 사용자 알림창 공통 JSON 응답
	 */
	private ResponseEntity<ErrorResponse> createAlertResponse(ErrorCode errorCode) {
		ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage(),
				errorCode.getHttpStatus());

		return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
	}

	/**
	 * 공통 ModelAndView 객체를 생성하는 메소드
	 *
	 * @param viewName 뷰 이름
	 * @param message  사용자에게 전달할 메시지
	 * @return 생성된 ModelAndView 객체
	 */
	private ModelAndView createModelAndView(String viewName, String message, String redirectPage) {
		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject("message", message);
		modelAndView.addObject("redirectPage", redirectPage);

		return modelAndView;
	}

	/**
	 * 공통 ModelAndView 객체를 생성하는 메소드
	 *
	 * @param viewName   뷰 이름
	 * @param message    사용자에게 전달할 메시지
	 * @param status     상태 코드
	 * @param httpStatus HTTP 상태
	 * @return 생성된 ModelAndView 객체
	 */
	private ModelAndView createErrorResponse(String viewName, ErrorCode errorCode) {
		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject("status", errorCode.getCode());
		modelAndView.addObject("message", errorCode.getMessage());
		modelAndView.setStatus(errorCode.getHttpStatus());

		return modelAndView;
	}

	/**
	 * 예외 발생 시 에러 로그를 기록하는 메소드
	 *
	 * @param ex 발생한 예외
	 */
	private void logError(Exception ex) {
		logger.error("{} occurred: {}", ex.getClass().getSimpleName(), ex.getMessage());
		logger.error("[Error] ", ex);
	}
}
