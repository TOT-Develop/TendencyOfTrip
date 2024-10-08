package tot.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tot.common.Constants;
import tot.common.enums.Flag;
import tot.common.page.PageReqDTO;
import tot.controller.TReviewController;
import tot.domain.CourseDTO;
import tot.domain.MemberVO;
import tot.domain.TReviewReqDTO;
import tot.domain.TReviewResDTO;
import tot.exception.InvalidMemberStatusException;
import tot.service.CommentService;
import tot.service.CourseService;
import tot.service.TReviewService;
import tot.service.TripService;

public class TReviewControllerTest {

	private TReviewController treviewController;
	private TReviewService treviewService;
	private TripService tripService;
	private CourseService courseService;
	private CommentService commentService;
	private HttpSession mockSession;
	private MemberVO validMember;
	private MemberVO invalidMember;
	private Model mockModel;

	private static final String MEMBER_ATTRIBUTE = "member";

	@BeforeEach
	public void setUp() {
		treviewService = mock(TReviewService.class);
		tripService = mock(TripService.class);
		courseService = mock(CourseService.class);
		commentService = mock(CommentService.class);

		treviewController = new TReviewController(treviewService, tripService, courseService, commentService);

		mockSession = mock(HttpSession.class);
		validMember = new MemberVO("testUser", "M01");
		invalidMember = new MemberVO("testUser", "M02");
		mockModel = mock(Model.class);

		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getSession()).thenReturn(mockSession);

		// RequestContextHolder 설정
		ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
		RequestContextHolder.setRequestAttributes(attributes);
	}

	@AfterEach
	public void tearDown() {
		RequestContextHolder.resetRequestAttributes();
	}

	private void setSessionMember(MemberVO member) {
		when(mockSession.getAttribute(MEMBER_ATTRIBUTE)).thenReturn(member);
	}

	/**
	 * 로그인하지 않은 사용자에 대해서 목록 페이지로 이동 테스트
	 * 
	 * @method showTourReview
	 */
	@Test
	public void testShowTourReview_WithoutAuthenticatedMember() {
		setSessionMember(null);

		String result = treviewController.showTourReview(1, new PageReqDTO(), mockModel);

		assertEquals(Constants.PAGE_TREVIEW, result, "로그인하지 않은 사용자가 목록 페이지에 접근할 수 있어야 합니다.");
	}

	/**
	 * 로그인하지 않은 사용자에 대해서 상세 페이지로 이동 테스트
	 * 
	 * @method showTourReviewDetail
	 */
	@Test
	public void testShowTourReviewDetail_WithoutAuthenticatedMember() {
		setSessionMember(null);
		TReviewResDTO mockReview = createMockReview();
		when(treviewService.getTReviewById(1)).thenReturn(mockReview);

		String result = treviewController.showTourReviewDetail("all", 1, mockModel);

		assertEquals(Constants.PAGE_DETAIL_TREVIEW, result, "로그인하지 않은 사용자가 상세 페이지에 접근할 수 있어야 합니다.");

		verify(mockModel).addAttribute(eq(MEMBER_ATTRIBUTE),
				argThat(member -> member instanceof MemberVO && ((MemberVO) member).getMemId() == null));
	}

	/**
	 * 글 작성 시 로그인한 회원인지 테스트
	 * 
	 * @method submitTourReviewWrite
	 */
	@Test
	public void testSubmitTourReviewWrite_WithAuthenticatedMember() {
		setSessionMember(validMember);

		ResponseEntity<Map<String, String>> result = treviewController.submitTourReviewWrite(new TReviewReqDTO(), null);

		assertEquals("여행 후기 글이 등록되었습니다.", result.getBody().get("message"), "로그인한 사용자가 글을 작성할 수 있어야 합니다.");
	}

	/**
	 * 글 작성 시 정상 회원인지 테스트
	 * 
	 * @method submitTourReviewWrite
	 */
	@Test
	public void testSubmitTourReviewWrite_InvalidMember() {
		setSessionMember(invalidMember);

		assertThrows(InvalidMemberStatusException.class, () -> {
			treviewController.submitTourReviewWrite(new TReviewReqDTO(), null);
		});
	}

	/**
	 * 글 작성 시 회원이 간 여행에 맞게 정보를 가져오는지 테스트
	 * 
	 * @method showTourReviewWrite
	 */
	@Test
	public void testSubmitTourReviewWrite_GetTripsForMember() {
		setSessionMember(validMember);
		when(tripService.getTripsByMemId(validMember.getMemId())).thenReturn(new ArrayList<>());

		treviewController.showTourReviewWrite(mockModel);

		verify(mockModel).addAttribute("trips", new ArrayList<>());
	}

	/**
	 * 글 작성 시 여행을 선택하면 해당 코스별로 정보가 잘 나오는지 테스트
	 * 
	 * @method getCourseDetailById
	 */
	@Test
	public void testGetCourseDetailById_ValidTripId() {
		int validTripId = 1;
		List<CourseDTO> expectedCourseList = new ArrayList<>();
		when(courseService.getCourseDetailsByTripId(validTripId)).thenReturn(expectedCourseList);

		List<CourseDTO> courses = treviewController.getCourseDetailById(validTripId);

		assertEquals(expectedCourseList, courses, "선택한 여행에 맞는 코스 정보가 잘 나와야 합니다.");
	}

	/**
	 * 글 수정 시 로그인한 회원인지 테스트
	 * 
	 * @method editTourReview
	 */
	@Test
	public void testEditTourReview_WithAuthenticatedMember() {
		setSessionMember(validMember);

		ResponseEntity<Map<String, String>> result = treviewController.editTourReview(1, new TReviewReqDTO(), null,
				null);

		assertEquals("여행 후기 글 수정이 완료되었습니다.", result.getBody().get("message"), "로그인한 사용자가 글을 수정할 수 있어야 합니다.");
	}

	/**
	 * 글 수정 시 정상 회원인지 테스트
	 * 
	 * @method editTourReview
	 */
	@Test
	public void testEditTourReview_InvalidMember() {
		setSessionMember(invalidMember);

		assertThrows(InvalidMemberStatusException.class, () -> {
			treviewController.editTourReview(1, new TReviewReqDTO(), null, null);
		});
	}

	/**
	 * 글 삭제 시 해당 글을 작성한 회원인지 테스트
	 * 
	 * @method deleteTourReview
	 */
	@Test
	public void testDeleteTourReview_AuthorCheck() {
		setSessionMember(validMember);
		TReviewResDTO mockReview = createMockReview();

		when(treviewService.getTReviewById(1)).thenReturn(mockReview);

		ResponseEntity<Map<String, String>> result = treviewController.deleteTourReview(1);

		assertEquals("여행 후기 글 삭제가 완료되었습니다.", result.getBody().get("message"), "해당 글의 작성자만 글을 삭제할 수 있어야 합니다.");
	}

	/**
	 * 글 신고 시 로그인한 회원인지 테스트
	 * 
	 * @method reportTourReview
	 */
	@Test
	public void testReportTourReview_WithAuthenticatedMember() {
		setSessionMember(validMember);

		ResponseEntity<Map<String, String>> result = treviewController.reportTourReview(1, "content", "reason");

		assertEquals("여행 후기 글 신고가 접수되었습니다.", result.getBody().get("message"), "로그인한 사용자가 글을 신고할 수 있어야 합니다.");
	}

	/**
	 * 글 신고 시 정상 회원인지 테스트
	 * 
	 * @method reportTourReview
	 */
	@Test
	public void testReportTourReview_InvalidMember() {
		setSessionMember(invalidMember);

		assertThrows(InvalidMemberStatusException.class, () -> {
			treviewController.reportTourReview(1, "content", "reason");
		});
	}

	private TReviewResDTO createMockReview() {
		return new TReviewResDTO(1, 1, validMember.getMemId(), "테스트유저", Flag.CMT001, "후기 제목", "코스", "후기 내용", "5",
				new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "10",
				new ArrayList<>());
	}
}
