package tot.common;

import java.util.regex.Pattern;

public class Constants {

	public static final int PAGE_ROW_COUNT = 8; // 페이지당 리뷰 개수
	public static final int PAGE_BLOCK_COUNT = 5; // 블록당 페이지 개수

	// 기본 URL
	public static final String BASIC_URL = "/tot";

	// 메인 페이지 및 URL
	public static final String PAGE_MAIN = "main";
	public static final String URL_MAIN = BASIC_URL + "/";

	// 로그인 페이지 및 URL
	public static final String PAGE_LOGIN = "login";
	public static final String URL_LOGIN = BASIC_URL + "/login";
	public static final String URL_GOOGLE_LOGIN = BASIC_URL + "/googlecallback";
	public static final String URL_KAKAO_LOGIN = BASIC_URL + "/kakaocallback";
	public static final String URL_NAVER_LOGIN = BASIC_URL + "/navercallback";

	// 회원 로그인 체크 URL
	public static final String URL_MEMBER_CHECKLOGIN = BASIC_URL + "/member/checkLogin";

	// 회원 성향 테스트 페이지 및 URL
	public static final String URL_TOT_TENDENCY_TEST = BASIC_URL + "/tendency";
	public static final String URL_TOT_TENDENCY_TEST_RESULT = BASIC_URL + "/tendencyTestResult";
	public static final String PAGE_TOT_TENDENCY_TEST = "tendencyTest";
	public static final String PAGE_TOT_TENDENCY_TEST_RESULT = "tendencyTestResult";

	// 추천 코스 페이지
	public static final String PAGE_TOT_RECOMMEND_COURSE = "recommendCourseInput";

	// 여행 추천 페이지
	public static final String PAGE_PLANNER = "planner";

	// 여행 코스 페이지
	public static final String PAGE_TRIPLIST = "tripList";
	public static final String PAGE_TRIP = "trip";

	// 문의글 페이지
	public static final String PAGE_QNA = "qna";
	public static final String PAGE_QNA_WRITE = "qnaRegist";
	public static final String PAGE_QNA_DETAIL = "qnaDetail";

	// 공지사항 페이지
	public static final String PAGE_NOTICE = "notice";
	public static final String PAGE_NOTICE_DETAIL = "noticeDetail";

	// 여행 후기 페이지 및 URL
	public static final String PAGE_TREVIEW = "treview";
	public static final String PAGE_WRITE_TREVIEW = "treviewWrite";
	public static final String PAGE_EDIT_TREVIEW = "treviewEdit";
	public static final String PAGE_DETAIL_TREVIEW = "treviewDetail";

	public static final String TREVIEW_BASE_URL = "/review";
	public static final String URL_ALL_TREVIEW = TREVIEW_BASE_URL + "/all/1";
	public static final String URL_MY_TREVIEW = TREVIEW_BASE_URL + "/my/1";

	public static final Pattern PATTERN_ALL_TREVIEW = Pattern.compile("^" + BASIC_URL + "/review/all/\\d+$");
	public static final Pattern PATTERN_ALL_DETAIL_TREVIEW = Pattern
			.compile("^" + BASIC_URL + "/review/all/detail/\\d+$");

	// 관리자 페이지

	// 관리자 로그인 페이지
	public static final String PAGE_ADMIN_LOGIN = "adminLogin";
	public static final String PAGE_ADMIN_USER = "adminUser";
	public static final String PAGE_ADMIN_BANUSER = "banUser";
	public static final String PAGE_ADMIN_KEY = "adminKey";

	// 관리자 후기 관리 페이지 (게시물, 댓글, 신고)
	public static final String PAGE_ADMIN_TREVIEW = "adminTReview";
	public static final String PAGE_ADMIN_EDIT_TREVIEW = "adminTReviewEdit";
	public static final String PAGE_ADMIN_DETAIL_TREVIEW = "adminTReviewDetail";
	public static final String PAGE_ADMIN_TREVIEW_COMMENT = "adminTReviewComment";
	public static final String PAGE_ADMIN_TREVIEW_REPORT = "adminTReviewReport";

	// 관리자 공지사항 페이지
	public static final String PAGE_ADMIN_NOTICE = "adminNotice";
	public static final String PAGE_ADMIN_NOTICE_DETAIL = "adminNoticeDetail";
	public static final String PAGE_ADMIN_NOTICE_REGIST = "adminNoticeRegist";
	public static final String PAGE_ADMIN_NOTICE_UPDATE = "adminNoticeUpdate";

	// 관리자 문의글 페이지 및 URL
	public static final String PAGE_ADMIN_QNA = "adminQna";
	public static final String PAGE_ADMIN_QNA_DETAIL = "adminQnaDetail";

	// 생성자를 private으로 설정하여 인스턴스화 방지
	private Constants() {
	}

}
