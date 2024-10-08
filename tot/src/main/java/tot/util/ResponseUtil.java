package tot.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

	/**
	 * 댓글에 대한 응답을 생성합니다.
	 * 
	 * @param message     응답 메시지
	 * @param updatedDate 댓글의 업데이트 날짜
	 * @return 댓글 응답을 포함하는 ResponseEntity
	 */
	public static ResponseEntity<Map<String, String>> createCommentResponse(String message, String updatedDate) {
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		response.put("updatedDate", updatedDate);

		return createResponse(HttpStatus.OK, response); // OK 상태와 함께 응답 반환
	}

	/**
	 * 여행 후기 응답을 생성합니다.
	 * 
	 * @param message 응답 메시지
	 * @return 여행 후기 응답을 포함하는 ResponseEntity
	 */
	public static ResponseEntity<Map<String, String>> createTReviewResponse(String message) {
		Map<String, String> response = new HashMap<>();
		response.put("message", message);

		return createResponse(HttpStatus.OK, response); // OK 상태와 함께 응답 반환
	}

	/**
	 * 공통 응답을 생성하는 메서드입니다.
	 * 
	 * @param status 응답 HTTP 상태 코드
	 * @param data   응답 데이터
	 * @return 주어진 상태 코드와 데이터를 포함하는 ResponseEntity
	 */
	private static ResponseEntity<Map<String, String>> createResponse(HttpStatus status, Map<String, String> data) {
		return new ResponseEntity<>(data, status); // 지정된 데이터와 상태로 응답 반환
	}
}
