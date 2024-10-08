package tot.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tot.domain.MemberVO;
import tot.exception.ErrorCode;
import tot.exception.InvalidMemberStatusException;
import tot.exception.MemberNotFoundException;
import tot.exception.ValidationException;

public class MemberUtil {

	/**
	 * 사용자의 로그인 여부를 확인합니다.
	 * 
	 * @return MemberVO 객체
	 */
	public static MemberVO getMember() {
		MemberVO member = (MemberVO) getSession().getAttribute("member");

		return (member != null) ? member : new MemberVO();
	}

	/**
	 * 인증된 사용자의 로그인 여부를 확인합니다.
	 * 
	 * @return 빈 MemberVO 객체
	 */
	public static MemberVO getAuthenticatedMember() {
		MemberVO member = (MemberVO) getSession().getAttribute("member");

		return (member != null) ? checkAuthenticatedMember(member) : new MemberVO();
	}

	/**
	 * 회원이 존재하지 않으면 예외를 던집니다.
	 * 
	 * @return 회원 정보
	 * @throws MemberNotFoundException 회원이 없을 경우 발생합니다.
	 */
	public static MemberVO isMember() {
		MemberVO member = (MemberVO) getSession().getAttribute("member");

		if (member == null) {
			throw new MemberNotFoundException();
		}

		return member;
	}
	
	/**
	 * 인증된 회원이 존재하지 않으면 예외를 던집니다.
	 * 
	 * @return 회원 정보
	 * @throws MemberNotFoundException 회원이 없을 경우 발생합니다.
	 */
	public static MemberVO isAuthenticatedMember() {
		MemberVO member = (MemberVO) getSession().getAttribute("member");

		if (member == null) {
			throw new MemberNotFoundException();
		}

		return checkAuthenticatedMember(member);
	}

	/**
	 * 로그인한 회원이 정상 회원인지 확인합니다.
	 * 
	 * @return 회원 정보
	 * @throws InvalidMemberStatusException 정상 회원이 아닐 경우 발생합니다.
	 */
	private static MemberVO checkAuthenticatedMember(MemberVO member) {
		if (!"M01".equals(member.getMember_status())) {
			throw new InvalidMemberStatusException();
		}

		return member;
	}

	/**
	 * 인증된 회원이 존재하는지 여부를 반환합니다.
	 * 
	 * @return 인증된 회원이 존재하면 true, 그렇지 않으면 false를 반환합니다.
	 */
	public static boolean isAutenticatedMemberLoggedIn() {
		try {
			isAuthenticatedMember();
			return true;
		} catch (MemberNotFoundException | InvalidMemberStatusException e) {
			return false;
		}
	}

	/**
	 * 회원의 닉네임 변경 시 중복 검사
	 * 
	 * @param nickname 회원 닉네임
	 * @throws ValidationException 값이 비유효 값과 일치할 경우 해당 오류 코드로 예외를 발생시킵니다.
	 */
	public static MemberVO validateDuplicateNickname(String nickname) {
		MemberVO member = getMember();
		if (member != null && member.getMemNick().equals(nickname)) {
			throw new ValidationException(ErrorCode.DUPLICATE_NICKNAME);
		}

		return member;
	}

	/**
	 * 회원 정보 업데이트
	 */
	public static void updateMemberInfo(MemberVO updatedMember) {
		getSession().setAttribute("member", updatedMember);
	}

	/**
	 * 일반 사용자인지 관리자인지 구분
	 */
	public static boolean isAdmin() {
		return getSession().getAttribute("admin") != null;
	}

	/**
	 * 관리자 정보 반환
	 */
	public static String getAdmin() {
		return getSession().getAttribute("admin").toString();
	}

	/**
	 * 현재 요청의 HttpSession을 가져오는 공통 메서드입니다.
	 * 
	 * @return 현재 세션
	 */
	private static HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(); // 세션 반환
	}
}
