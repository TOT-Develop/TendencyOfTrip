package tot.service;

import java.util.Map;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.MemberVO;

public interface MemberService {

	void saveMember(String socialId, String nickname, String email);

	void updateNickname(String memId, String newNickname);

	void applyBan(String memId, String banStatus, String banReason);

	void saveNickname(String memId, String nickname, String email);

	MemberVO findMemberByMemId(String memId);

	PageResDTO<MemberVO> getAllMembers(PageReqDTO pageReqDTO);

	void updateMemberStatus(Map<String, Object> params);

}
