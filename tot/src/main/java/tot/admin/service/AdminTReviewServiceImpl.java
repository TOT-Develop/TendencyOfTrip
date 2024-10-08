package tot.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.admin.dao.AdminTReviewDao;
import tot.admin.enums.BanReason;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.TReviewResDTO;
import tot.service.EmailService;

@Service
public class AdminTReviewServiceImpl implements AdminTReviewService {

	private final AdminTReviewDao adminTReviewDao;
	private final EmailService emailService;

	public AdminTReviewServiceImpl(AdminTReviewDao adminTReviewDao, EmailService emailService) {
		this.adminTReviewDao = adminTReviewDao;
		this.emailService = emailService;
	}

	/**
	 * 페이지네이션된 여행 후기 목록을 조회합니다.
	 *
	 * @param pageReqDTO 페이지 요청 데이터 전송 객체
	 * @param boardId    게시판 ID
	 * @return 페이지네이션된 여행 후기 응답 객체
	 */
	@Override
	public PageResDTO<TReviewResDTO> findTReviewListWithPaging(PageReqDTO pageReqDTO, int boardId) {
		PageDTO pageDTO = new PageDTO(pageReqDTO, boardId);
		int totalTReviewCount = adminTReviewDao.selectTotalTReviewCount(pageDTO);

		List<TReviewResDTO> postList = adminTReviewDao.selectTReviewListWithPaging(pageDTO);

		return new PageResDTO<>(totalTReviewCount, pageReqDTO.getPage(), postList);
	}

	/**
	 * 여행 후기 상태를 업데이트합니다.
	 *
	 * @param status  상태 (예: 활성화/비활성화)
	 * @param trevIds 업데이트할 여행 후기 ID 목록
	 */
	@Override
	public void updateTReviewStatus(String status, List<Integer> trevIds, BanReason reason) {
		adminTReviewDao.updateTReviewStatus(status, trevIds);

		for (Integer trevId : trevIds) {
			if (status.equals("deactive")) {
				String memberEmail = findMemberEmailByTrevId(trevId);
				String postTitle = getTReviewById(trevId).getTrevTitle();
				emailService.sendSanctionEmail(memberEmail, postTitle, reason.getDescription());
			}
		}
	}

	/**
	 * 특정 여행 후기를 ID로 조회합니다.
	 *
	 * @param trevId 여행 후기 ID
	 * @return 여행 후기 응답 데이터 전송 객체
	 */
	@Override
	public TReviewResDTO getTReviewById(int trevId) {
		return adminTReviewDao.getTReviewById(trevId);
	}

	// 신고 대상 회원 이메일을 찾는 메서드
	private String findMemberEmailByTrevId(Integer trevId) {
		return adminTReviewDao.findMemberEmailByTrevId(trevId);
	}

}
