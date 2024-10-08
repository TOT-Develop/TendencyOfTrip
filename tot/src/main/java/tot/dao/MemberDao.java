package tot.dao;

import java.util.List;
import java.util.Map;

import tot.common.page.PageDTO;
import tot.domain.MemberVO;

public interface MemberDao {

	// 사용자 정보 저장
	void saveMember(MemberVO member);

	// 사용자 닉네임 업데이트
	void updateNickname(String memId, String newNickname);

	// 사용자의 닉네임이 존재하는지 확인
	String findNicknameByMemId(String memId);

	// 닉네임 중복 체크
	String findNicknameByNick(String nickname);

	// MEMID로 사용자 정보 전체 조회
	MemberVO findMemberByMemId(String memId);

	// 회원 상태와 제재 기간 업데이트
	void updateMemberStatus(Map<String, Object> params);

	List<MemberVO> getMembersWithPaging(PageDTO pageDTO);

	int getTotalMemberCount(PageDTO pageDTO);

}
