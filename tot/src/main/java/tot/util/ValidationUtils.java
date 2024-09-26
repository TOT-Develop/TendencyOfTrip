package tot.util;

import tot.exception.ValidationException;
import tot.dao.MemberDao;
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
	
	// 닉네임 중복 검사
    public static void validateDuplicateNickname(String nickname, MemberDao memberDao, ErrorCode errorCode) {
        String existingNickname = memberDao.findNicknameByNick(nickname);
        if (existingNickname != null) {
        	System.out.println("중복");
            throw new ValidationException(errorCode);  // 중복일 경우 예외 발생
        }
	}
}
