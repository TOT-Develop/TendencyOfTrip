package tot.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tot.domain.MemberVO;
import tot.exception.InvalidMemberStatusException;
import tot.exception.MemberNotFoundException;
import tot.util.MemberUtil;

public class MemberUtilTest {

	private HttpSession mockSession; // Mock HttpSession 객체
	private MemberVO mockMember; // Mock MemberVO 객체

	@BeforeEach
	public void setUp() {
		mockSession = mock(HttpSession.class);
		mockMember = new MemberVO("testUser", "M01"); // 정상 회원 상태로 생성

		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getSession()).thenReturn(mockSession);

		// 현재 요청 속성을 설정하여 RequestContextHolder에 등록
		ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
		RequestContextHolder.setRequestAttributes(attributes);
	}
	
	 /**
     * 로그인된 회원 정보가 있는 경우 올바른 회원 정보를 반환하는지 테스트
     * 
     * @method getAuthenticatedMember
     * @return MemberVO 회원 정보
     */
    @Test
    public void testGetAuthenticatedMember_WithAuthenticatedMember() {
        when(mockSession.getAttribute("member")).thenReturn(mockMember);

        MemberVO result = MemberUtil.getAuthenticatedMember();
        assertNotNull(result, "인증된 회원 정보는 null이 아니어야 합니다.");
        assertEquals(mockMember, result, "반환된 회원 정보는 설정한 mockMember와 같아야 합니다.");
    }

    /**
     * 로그인된 회원 정보가 없는 경우 빈 회원 정보를 반환하는지 테스트
     * 
     * @method getAuthenticatedMember
     * @return 빈 MemberVO
     */
    @Test
    public void testGetAuthenticatedMember_WithoutAuthenticatedMember() {
        when(mockSession.getAttribute("member")).thenReturn(null);

        MemberVO result = MemberUtil.getAuthenticatedMember();
        assertNotNull(result, "빈 MemberVO 객체는 null이 아니어야 합니다.");
        assertTrue(result.isEmpty(), "반환된 빈 MemberVO 객체는 새로운 빈 MemberVO 객체와 같아야 합니다.");
    }

	/**
	 * 인증된 회원이 존재하는 경우 올바른 회원 정보를 반환하는지 테스트
	 * 
	 * @method isAuthenticatedMember
	 * @return MemberVO
	 */
	@Test
	public void testCheckAuthenticatedMember_WithAuthenticatedMember() {
		when(mockSession.getAttribute("member")).thenReturn(mockMember);

		MemberVO result = MemberUtil.isAuthenticatedMember();
		assertNotNull(result, "인증된 회원 정보는 null이 아니어야 합니다.");
		assertEquals(mockMember, result, "반환된 회원 정보는 설정한 mockMember와 같아야 합니다.");
	}

	/**
	 * 인증된 회원이 존재하지 않는 경우 예외 발생 테스트
	 * 
	 * @method isAuthenticatedMember
	 * @return MemberNotFoundException
	 */
	@Test
	public void testCheckAuthenticatedMember_WithoutAuthenticatedMember() {
		when(mockSession.getAttribute("member")).thenReturn(null);

		assertThrows(MemberNotFoundException.class, MemberUtil::isAuthenticatedMember, "인증된 회원이 없으므로 예외가 발생해야 합니다.");
	}

	/**
	 * 인증된 회원이 비정상 회원인 경우 예외 발생 테스트
	 * 
	 * @method isAuthenticatedMember
	 * @return InvalidMemberStatusException
	 */
	@Test
	public void testCheckAuthenticatedMember_WithAbnormalMember() {
		mockMember = new MemberVO("testUser", "M02");
		when(mockSession.getAttribute("member")).thenReturn(mockMember);

		assertThrows(InvalidMemberStatusException.class, MemberUtil::isAuthenticatedMember,
				"정상 회원이 아니므로 예외가 발생해야 합니다.");
	}

	/**
	 * 인증된 회원이 존재하는 경우 true를 반환하는지 테스트
	 * 
	 * @method isMemberLoggedIn
	 * @return true
	 */
	@Test
	public void testIsMemberLoggedIn_WithAuthenticatedMember() {
		when(mockSession.getAttribute("member")).thenReturn(mockMember);

		boolean result = MemberUtil.isAutenticatedMemberLoggedIn();
		assertTrue(result, "로그인 회원이 존재하므로 true가 반환되어야 합니다.");
	}

	/**
	 * 인증된 회원이 존재하지 않는 경우 false를 반환하는지 테스트
	 * 
	 * @method isMemberLoggedIn
	 * @return false
	 */
	@Test
	public void testIsMemberLoggedIn_WithoutAuthenticatedMember() {
		when(mockSession.getAttribute("member")).thenReturn(null);

		boolean result = MemberUtil.isAutenticatedMemberLoggedIn();
		assertFalse(result, "로그인 회원이 존재하지않으므로 false가 반환되어야 합니다.");
	}

}
