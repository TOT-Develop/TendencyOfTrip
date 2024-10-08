package tot.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tot.domain.TReviewReqDTO;
import tot.exception.ValidationException;

public class TReviewReqDTOTest {

	/**
	 * 제목이 비어있을 때 예외 발생 테스트
	 * 
	 * @return NOT_FOUND_TREVTITLE
	 */
	@Test
	public void testValidate_TrevTitle_Empty() {
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", "", "Some course", "Some content", "5", "/path/to/img",
				"agree");

		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 여행이 비어있을 때 예외 발생 테스트
	 * 
	 * @return NOT_FOUND_TRIPID
	 */
	@Test
	public void testValidate_TrevId_Empty() {
		TReviewReqDTO dto = new TReviewReqDTO(0, 0, "mem01", "Some title", "Some course", "Some content", "5",
				"/path/to/img", "agree");

		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 내용이 비어있을 때 예외 발생 테스트
	 * 
	 * @return NOT_FOUND_TREVCONTENT
	 */
	@Test
	public void testValidate_TrevContent_Empty() {
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", "Some title", "Some course", "", "5", "/path/to/img",
				"agree");

		// ValidationException이 발생하는지 확인
		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 제목이 너무 길 때 예외 발생 테스트
	 * 
	 * @return TITLE_TOO_LONG
	 */
	@Test
	public void testValidate_TrevTitle_TooLong() {
		String longTitle = "A".repeat(201);
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", longTitle, "Some course", "Some content", "5",
				"/path/to/img", "agree");

		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 내용이 너무 길 때 예외 발생 테스트
	 * 
	 * @return CONTENT_TOO_LONG
	 */
	@Test
	public void testValidate_TrevContent_TooLong() {
		String longContent = "A".repeat(4001);
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", "Some title", "Some course", longContent, "5",
				"/path/to/img", "agree");

		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 개인정보 동의 여부가 비어있을 때 예외 발생 테스트
	 * 
	 * @return NOT_FOUND_TREVAGREE
	 */
	@Test
	public void testValidate_TrevAgree_Empty() {
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", "Some title", "Some course", "Some content", "5",
				"/path/to/img", "");

		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 개인정보 동의 여부가 'disagree'일 때 예외 발생 테스트
	 * 
	 * @return NOT_CHECK_TREVAGREE
	 */
	@Test
	public void testValidate_TrevAgree_Disagree() {
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", "Some title", "Some course", "Some content", "5",
				"/path/to/img", "disagree");

		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 평점이 비어있을 때 예외 발생 테스트
	 * 
	 * @return NOT_CHECK_TREVRATING
	 */
	@Test
	public void testValidate_TrevRating_Disagree() {
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", "Some title", "Some course", "Some content", null,
				"/path/to/img", "agree");

		assertThrows(ValidationException.class, dto::validate);
	}

	/**
	 * 유효한 데이터일 때 정상 동작 테스트
	 */
	@Test
	public void testValidate_ValidData() {
		TReviewReqDTO dto = new TReviewReqDTO(1, 1, "mem01", "Some title", "Some course", "Some content", "5",
				"/path/to/img", "agree");

		assertDoesNotThrow(dto::validate);
	}
}
