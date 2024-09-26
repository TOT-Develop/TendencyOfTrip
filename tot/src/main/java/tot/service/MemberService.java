package tot.service;

import java.util.Map;

import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.MemberVO;
import tot.exception.ValidationException;

public interface MemberService {
	public abstract void saveMember(String socialId, String nickname, String email);

	public abstract void updateNickname(String memId, String newNickname) throws ValidationException;

	public abstract void applyBan(String memId, String banStatus, String banReason);

	public abstract void saveNickname(String memId, String nickname, String email) throws ValidationException;
    
	public abstract MemberVO findMemberByMemId(String memId);

    public abstract PageResDTO<MemberVO> getAllMembers(PageReqDTO pageReqDTO);

    public abstract void updateMemberStatus(Map<String, Object> params);
}
