package tot.util;

import tot.exception.ErrorCode;
import tot.exception.ValidationException;

public class ValidationUtil {

	/**
	 * 문자열로 주어진 값이 비어있지 않은지 검증합니다.
	 * 
	 * @param value     검증할 값
	 * @param errorCode 발생할 오류 코드
	 * @throws ValidationException 값이 비어있을 경우 해당 오류 코드로 예외를 발생시킵니다.
	 */
	public static void validateNotEmpty(String value, ErrorCode errorCode) {
		if (value == null || value.trim().isEmpty()) {
			throw new ValidationException(errorCode);
		}
	}

	/**
	 * 숫자로 주어진 값이 비어있지 않은지 검증합니다.
	 * 
	 * @param value     검증할 값
	 * @param errorCode 발생할 오류 코드
	 * @throws ValidationException 값이 비어있을 경우 해당 오류 코드로 예외를 발생시킵니다.
	 */
	public static void validateNotEmpty(int value, ErrorCode errorCode) {
		if (value <= 0) {
			throw new ValidationException(errorCode);
		}
	}

	/**
	 * 주어진 값의 길이가 최대 길이를 초과하지 않는지 검증합니다.
	 * 
	 * @param value     검증할 값
	 * @param maxLength 최대 길이
	 * @param errorCode 발생할 오류 코드
	 * @throws ValidationException 값의 길이가 최대 길이를 초과할 경우 해당 오류 코드로 예외를 발생시킵니다.
	 */
	public static void validateLength(String value, int maxLength, ErrorCode errorCode) {
		if (value != null && value.length() > maxLength) {
			throw new ValidationException(errorCode);
		}
	}

	/**
	 * 특정 값이 주어진 비유효 값과 일치하는지 검증합니다.
	 * 
	 * @param value        검증할 값
	 * @param invalidValue 비유효 값
	 * @param errorCode    발생할 오류 코드
	 * @throws ValidationException 값이 비유효 값과 일치할 경우 해당 오류 코드로 예외를 발생시킵니다.
	 */
	public static void validateCheck(String value, String invalidValue, ErrorCode errorCode) {
		if (value != null && value.equals(invalidValue)) {
			throw new ValidationException(errorCode);
		}
	}

}
