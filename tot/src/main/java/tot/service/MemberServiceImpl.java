package tot.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.dao.MemBanHistoryDao;
import tot.dao.MemberDao;
import tot.domain.MemBanHistoryDTO;
import tot.domain.MemberVO;
import tot.exception.ErrorCode;
import tot.exception.ValidationException;

@Service
public class MemberServiceImpl implements MemberService {

	private final MemberDao memberDao;
	private final MemBanHistoryDao memBanHistoryDao;

	public MemberServiceImpl(MemberDao memberDao, MemBanHistoryDao memBanHistoryDao) {
		this.memberDao = memberDao;
		this.memBanHistoryDao = memBanHistoryDao;
	}

	// 회원 저장 메서드
	@Override
	public void saveMember(String socialId, String nickname, String email) {
		MemberVO member = new MemberVO(socialId, nickname, email, new java.sql.Timestamp(System.currentTimeMillis()));

		memberDao.saveMember(member);
	}

	// 회원 찾기
	@Override
	public MemberVO findMemberByMemId(String memId) {
		return memberDao.findMemberByMemId(memId);
	}

	// 닉네임 업데이트 메서드
	@Override
	public void updateNickname(String memId, String newNickname) {
		String existingMemNick = memberDao.findNicknameByNick(newNickname);
		if (existingMemNick == null) {
			memberDao.updateNickname(memId, newNickname);
		} else {
			throw new ValidationException(ErrorCode.DUPLICATE_NICKNAME);
		}
	}

	// 제재 상태 적용 메서드
	@Override
	public void applyBan(String memId, String banStatus, String banReason) {
		long banPeriodDays = 3;
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		Timestamp banEndTimestamp = new Timestamp(currentTimestamp.getTime() + banPeriodDays * 24 * 60 * 60 * 1000);

		// 파라미터로 전달할 객체 생성
		Map<String, Object> params = new HashMap<>();
		params.put("memId", memId);
		params.put("memberStatus", banStatus);
		params.put("banStart", currentTimestamp);
		params.put("banEnd", banEndTimestamp);

		// 멤버 테이블 업데이트
		memberDao.updateMemberStatus(params);

		// 히스토리 테이블에 기록
		MemBanHistoryDTO banHistory = new MemBanHistoryDTO();
		banHistory.setMemId(memId);
		banHistory.setBanReason(banReason);
		banHistory.setBanStart(currentTimestamp);
		banHistory.setBanEnd(banEndTimestamp);

		memBanHistoryDao.insertBanHistory(banHistory);
	}

	// 닉네임 저장 및 중복 검사
	@Override
	public void saveNickname(String memId, String nickname, String email) {
		String existingMemNick = memberDao.findNicknameByNick(nickname);
		if (existingMemNick == null) {
			MemberVO newMember = new MemberVO(memId, nickname, email,
					new java.sql.Timestamp(System.currentTimeMillis()));
			memberDao.saveMember(newMember);
			memberDao.updateNickname(memId, nickname);
		} else {
			throw new ValidationException(ErrorCode.DUPLICATE_NICKNAME);
		}
	}

	@Override
	public PageResDTO<MemberVO> getAllMembers(PageReqDTO pageReqDTO) {
		PageDTO pageDTO = new PageDTO(pageReqDTO); // PageReqDTO를 PageDTO로 변환
		System.out.println("서비스" + pageDTO);
		// 페이징된 회원 목록 가져오기
		List<MemberVO> members = memberDao.getMembersWithPaging(pageDTO); // 페이징된 회원 목록 가져오기
		System.out.println(members);
		// 전체 회원 수 가져오기
		int totalMemberCount = memberDao.getTotalMemberCount(pageDTO); // 전체 회원 수 가져오기

		// PageResDTO 반환
		return new PageResDTO<>(totalMemberCount, pageReqDTO.getPage(), members); // 현재 페이지를 pageReqDTO에서 가져오기
	}

	@Override
	public void updateMemberStatus(Map<String, Object> params) {
		memberDao.updateMemberStatus(params);
	}

}
