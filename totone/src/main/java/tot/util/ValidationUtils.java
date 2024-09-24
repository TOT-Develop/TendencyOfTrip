package tot.util;

import tot.exception.ValidationException;
import tot.exception.ErrorCode;

public class ValidationUtils {

	public static void validateNotEmpty(String value, ErrorCode errorCode) {
		if (value == null || value.trim().isEmpty()) {
			throw new ValidationException(errorCode);
		}
	}

	public static void validateLength(String value, int maxLength, ErrorCode errorCode) {
		if (value != null && value.length() > maxLength) {
			throw new ValidationException(errorCode);
		}
	}
	
	public static void validateCheck(String value, ErrorCode errorCode) {
		if (value != null && value.equals("disagree")) {
			throw new ValidationException(errorCode);
		}
	}
}
