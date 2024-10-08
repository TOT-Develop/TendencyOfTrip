package tot.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tot.controller.CommentController;
import tot.domain.CommentReqDTO;
import tot.domain.MemberVO;
import tot.exception.ErrorCode;
import tot.exception.InvalidMemberStatusException;
import tot.exception.MemberNotFoundException;
import tot.exception.ValidationException;
import tot.service.CommentService;

public class CommentControllerTest {

	private CommentController commentController;
	private CommentService commentService;
	private HttpSession mockSession;
	private MemberVO validMember;
	private MemberVO invalidMember;
	private Model mockModel;

	private static final String MEMBER_ATTRIBUTE = "member";

	@BeforeEach
	public void setUp() {
		commentService = mock(CommentService.class);
		commentController = new CommentController(commentService);

		mockSession = mock(HttpSession.class);
		validMember = new MemberVO("testUser", "M01");
		invalidMember = new MemberVO("testUser", "M02");
		mockModel = mock(Model.class);

		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getSession()).thenReturn(mockSession);

		// RequestContextHolder 설정
		RequestAttributes requestAttributes = new ServletRequestAttributes(mockRequest);
		RequestContextHolder.setRequestAttributes(requestAttributes);
	}

	@AfterEach
	public void tearDown() {
		RequestContextHolder.resetRequestAttributes();
	}

	private void setSessionMember(MemberVO member) {
		when(mockSession.getAttribute(MEMBER_ATTRIBUTE)).thenReturn(member);
	}

	/**
	 * 댓글 작성 시 유효한 사용자인 경우 동작 테스트
	 * 
	 * @method addComment
	 * @return 로그인한 경우 MemberVO 회원 정보
	 * @return 로그인하지 않은 경우 MemberNotFoundException
	 */
	@Test
	public void testAddComment_WithAuthenticatedMember() {
		setSessionMember(validMember);
		CommentReqDTO commentReqDTO = new CommentReqDTO(0, "", "댓글 내용");

		String result = commentController.addComment("boardId", 1, commentReqDTO, mockModel);

		assertEquals("redirect:/review/boardId/detail/1", result, "로그인한 사용자가 댓글을 작성할 수 있어야 합니다.");
		verify(commentService).insertComment("boardId", 1, commentReqDTO);
	}

	/**
	 * 댓글 작성 시 로그인하지 않은 사용자인 경우 예외 처리 테스트
	 * 
	 * @method addComment
	 * @return 로그인한 경우 MemberVO 회원 정보
	 * @return 로그인하지 않은 경우 MemberNotFoundException
	 */
	@Test
	public void testAddComment_NotAuthenticated() {
		setSessionMember(null);

		CommentReqDTO commentReqDTO = new CommentReqDTO(0, "", "댓글 내용");

		assertThrows(MemberNotFoundException.class, () -> {
			commentController.addComment("boardId", 1, commentReqDTO, mockModel);
		});
	}

	/**
	 * 댓글 작성 시 정상 회원이 아닌 경우 예외 처리 테스트
	 * 
	 * @method addComment
	 * @return 정상인 경우 MemberVO 회원 정보
	 * @return 아닌 경우 InvalidMemberStatusException
	 */
	@Test
	public void testAddComment_WithInvalidMember() {
		setSessionMember(invalidMember);
		CommentReqDTO commentReqDTO = new CommentReqDTO(0, "", "댓글 내용");

		assertThrows(InvalidMemberStatusException.class, () -> {
			commentController.addComment("boardId", 1, commentReqDTO, mockModel);
		});
	}

	/**
	 * 댓글 수정 시 해당 댓글을 작성한 회원의 동작 테스트
	 * 
	 * @method editComment
	 */
	@Test
	public void testEditComment_WithAuthenticatedMember() {
		setSessionMember(validMember);
		int commentId = 1;

		doNothing().when(commentService).editComment(commentId, "수정된 댓글 내용");

		ResponseEntity<Map<String, String>> result = commentController.editComment(commentId, "수정된 댓글 내용");

		assertEquals(HttpStatus.OK, result.getStatusCode(), "댓글을 수정할 수 있어야 합니다.");
		verify(commentService).editComment(commentId, "수정된 댓글 내용");
	}

	/**
	 * 댓글 수정 시 인증되지 않은 회원일 경우의 동작을 테스트
	 * 
	 * @method editComment
	 */
	@Test
	public void testEditComment_WithInvalidMember() {
		setSessionMember(invalidMember);
		int commentId = 1;

		assertThrows(InvalidMemberStatusException.class, () -> {
			commentController.editComment(commentId, "수정된 댓글 내용");
		});
	}

	/**
	 * 댓글 삭제 시 해당 댓글을 작성한 회원의 경우 동작 테스트
	 * 
	 * @method deleteComment
	 */
	@Test
	public void testDeleteComment_WithAuthenticatedMember() {
		setSessionMember(validMember);
		int commentId = 1;

		doNothing().when(commentService).deleteComment(commentId);

		ResponseEntity<Map<String, String>> result = commentController.deleteComment(commentId);

		assertEquals(HttpStatus.OK, result.getStatusCode(), "댓글을 삭제할 수 있어야 합니다.");
		verify(commentService).deleteComment(commentId);
	}

	/**
	 * 댓글 삭제 시 인증되지 않은 회원일 경우의 동작을 테스트
	 * 
	 * @method deleteComment
	 */
	@Test
	public void testDeleteComment_WithInvalidMember() {
		setSessionMember(invalidMember);
		int commentId = 1;

		assertThrows(InvalidMemberStatusException.class, () -> {
			commentController.deleteComment(commentId);
		});
	}

	/**
	 * 댓글 수정 시 유효한 사용자인 경우 테스트
	 * 
	 * @method reportComment
	 * @return 로그인한 경우 MemberVO 회원 정보
	 * @return 로그인하지 않은 경우 MemberNotFoundException
	 */
	@Test
	public void testReportComment_WithAuthenticatedMember() {
		setSessionMember(validMember);
		int commentId = 1;

		ResponseEntity<Map<String, String>> result = commentController.reportComment(commentId, "contentType",
				"reportReason");

		assertEquals(HttpStatus.OK, result.getStatusCode(), "로그인한 사용자가 댓글을 신고할 수 있어야 합니다.");
		assertEquals("신고가 접수되었습니다.", result.getBody().get("message"), "신고 메시지가 정상적으로 반환되어야 합니다.");
		verify(commentService).reportComment(commentId, "contentType", "reportReason");
	}

	/**
	 * 댓글 수정 시 로그인하지 않은 사용자인 경우 예외 처리 테스트
	 * 
	 * @method reportComment
	 * @return 로그인한 경우 MemberVO 회원 정보
	 * @return 로그인하지 않은 경우 MemberNotFoundException
	 */
	@Test
	public void testReportComment_NotAuthenticated() {
		setSessionMember(null);
		int commentId = 1;

		assertThrows(MemberNotFoundException.class, () -> {
			commentController.reportComment(commentId, "contentType", "reportReason");
		});
	}

	/**
	 * 댓글 수정 시 정상 회원이 아닌 경우 예외 처리 테스트
	 * 
	 * @method reportComment
	 * @return 정상인 경우 MemberVO 회원 정보
	 * @return 아닌 경우 InvalidMemberStatusException
	 */
	@Test
	public void testReportComment_WithInvalidMember() {
		setSessionMember(invalidMember);
		int commentId = 1;

		assertThrows(InvalidMemberStatusException.class, () -> {
			commentController.reportComment(commentId, "contentType", "reportReason");
		});
	}

	/**
	 * 댓글 신고 시 댓글 사유를 작성했는지 확인하는 테스트
	 * 
	 * @method reportComment
	 */
	@Test
	public void testReportComment_WithoutReason() {
		setSessionMember(validMember);
		int commentId = 1;

		ValidationException exception = assertThrows(ValidationException.class, () -> {
			commentController.reportComment(commentId, "contentType", "");
		});

		assertEquals(ErrorCode.NOT_FOUND_REPORTREASON, exception.getErrorCode());
	}

	/**
	 * 댓글 신고가 정상적으로 작동하는지 테스트
	 * 
	 * @method reportComment
	 */
	@Test
	public void testReportComment_Success() {
		setSessionMember(validMember);
		int commentId = 1;

		ResponseEntity<Map<String, String>> result = commentController.reportComment(commentId, "contentType",
				"부적절한 내용");

		assertEquals(HttpStatus.OK, result.getStatusCode(), "댓글 신고가 정상적으로 작동해야 합니다.");
		assertEquals("신고가 접수되었습니다.", result.getBody().get("message"), "신고 메시지가 정상적으로 반환되어야 합니다.");
		verify(commentService).reportComment(commentId, "contentType", "부적절한 내용");
	}

}
