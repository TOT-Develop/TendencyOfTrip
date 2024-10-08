package tot.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.admin.dao.AdminReportDao;
import tot.admin.enums.BanReason;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.ReportDTO;
import tot.service.EmailService;

@Service
public class AdminReportServiceImpl implements AdminReportService {

	private final AdminReportDao adminReportDao;
	private final EmailService emailService;

	public AdminReportServiceImpl(AdminReportDao adminReportDao, EmailService emailService) {
		this.adminReportDao = adminReportDao;
		this.emailService = emailService;
	}

	/**
	 * 페이지네이션된 신고 목록을 조회합니다.
	 *
	 * @param pageReqDTO 페이지 요청 데이터 전송 객체
	 * @param boardId    게시판 ID
	 * @return 페이지네이션된 신고 응답 객체
	 */
	@Override
	public PageResDTO<ReportDTO> findReportListWithPaging(PageReqDTO pageReqDTO, int boardId) {
		PageDTO pageDTO = new PageDTO(pageReqDTO, boardId);
		int totalTReviewCount = adminReportDao.selectTotalReportCount(pageDTO);

		List<ReportDTO> reportList = adminReportDao.findReportListWithPaging(pageDTO);

		return new PageResDTO<>(totalTReviewCount, pageReqDTO.getPage(), reportList);
	}

	/**
	 * 신고 상태를 업데이트합니다.
	 * 게시물 제재할 경우 해당 회원에게 제재 이메일을 전송합니다.
	 *
	 * @param status    상태 (예: 활성화/비활성화)
	 * @param reportIds 업데이트할 신고 ID 목록
	 * @param reason 제재 사유
	 */
	@Override
	public void updateReportStatus(String status, List<Integer> reportIds, BanReason reason) {
		adminReportDao.updateReportStatus(status, reportIds);

		for (Integer reportId : reportIds) {
			String contentType = findReportedContentTypeByReportId(reportId);
			
			if (status.equals("COMPLETED") && "Treview".equals(contentType)) {
				String memberEmail = findMemberEmailByReportId(reportId);
				String postTitle = findPostTitleByReportId(reportId);
				emailService.sendSanctionEmail(memberEmail, postTitle, reason.getDescription());
			}
		}
	}
	
	// 신고된 콘텐츠 유형을 가져오는 메서드
	private String findReportedContentTypeByReportId(Integer reportId) {
	    return adminReportDao.findReportedContentTypeByReportId(reportId);
	}

	// 신고 대상 회원 이메일을 찾는 메서드
	private String findMemberEmailByReportId(Integer reportId) {
		return adminReportDao.findMemberEmailByReportId(reportId);
	}

	// 신고 게시물 제목을 찾는 메서드
	private String findPostTitleByReportId(Integer reportId) {
		return adminReportDao.findPostTitleByReportId(reportId);
	}

}
