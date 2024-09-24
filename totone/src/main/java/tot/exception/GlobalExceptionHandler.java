package tot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	// 값 유효성 검사 예외 처리
    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(ValidationException e) {
        String errorMessage = e.getErrorCode().getMessage();

        return getModelAndView(errorMessage, "common");
    }

    // 서버 오류 예외 처리
    @ExceptionHandler(ServerException.class)
    public ModelAndView handleServerException(ServerException e, Throwable cause) {
        String errorMessage = e.getMessage();
        System.out.println("[Error] " + cause.getCause());
        
        return getModelAndView(errorMessage, "errorPage");
    }
    
    // 일반 예외 처리
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception e) {
        String errorMessage = "서버에서 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
        System.out.println("[Error] " + e.getMessage());
        
        return getModelAndView(errorMessage, "errorPage");
    }
    
	// 경고창으로 보내기 위한 에러메시지와 뷰 전달
	private ModelAndView getModelAndView(String message, String viewName) {
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("message", message);
		return mav;
	}

}
