package tot.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tot.common.enums.ProcessStatus;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.dao.TReviewDao;
import tot.domain.DelegatingMultipartFile;
import tot.domain.HistoryVO;
import tot.domain.MemberVO;
import tot.domain.ReportTReviewDTO;
import tot.domain.ReportVO;
import tot.domain.TReviewImageVO;
import tot.domain.TReviewReqDTO;
import tot.domain.TReviewResDTO;
import tot.domain.TReviewVO;
import tot.util.FileUtil;
import tot.util.MemberUtil;

@Service
public class TReviewServiceImpl implements TReviewService {

	private final TReviewDao treviewDao;
	private final HistoryService historyService;
	private final FileUtil fileUtils;

	public TReviewServiceImpl(TReviewDao treviewDao, HistoryService historyService, FileUtil fileUtils) {
		this.treviewDao = treviewDao;
		this.historyService = historyService;
		this.fileUtils = fileUtils;
	}

	@Value("${file.upload-dir}")
	private String uploadDir;

	/**
	 * 새로운 여행 후기를 등록하고 관련 이미지를 저장합니다.
	 *
	 * @param treviewReqDTO 여행 후기 요청 데이터 전송 객체
	 * @param imageFiles    업로드할 이미지 파일 배열
	 */
	@Override
	public void insertTReview(TReviewReqDTO treviewReqDTO, MultipartFile[] imageFiles) {
		MemberVO member = MemberUtil.isAuthenticatedMember();

		// DTO 검증
		treviewReqDTO.validate();

		TReviewVO tReviewVO = TReviewVO.fromDTO(treviewReqDTO);
		treviewDao.insertTReview(tReviewVO);

		// 이미지 파일 처리
		TReviewImageVO tReviewImageVO = new TReviewImageVO();
		treviewReqDTO.setTrevId(tReviewVO.getTrevId());
		if (imageFiles != null && imageFiles.length > 0) {
			for (MultipartFile imageFile : imageFiles) {
				if (!imageFile.isEmpty()) {
					String imagePath = fileUtils.saveImage(imageFile);
					treviewReqDTO.setTrevImgpath(imagePath);
					tReviewImageVO = TReviewImageVO.fromDTO(treviewReqDTO);

					treviewDao.insertTReviewImage(tReviewImageVO);
				}
			}
		}
		saveHistory(tReviewVO.getTrevId(), tReviewVO.getMemId(), member.getMemNick(), "등록", tReviewVO.getTrevContent(),
				null);
	}

	/**
	 * 페이지네이션된 여행 후기 목록을 가져옵니다.
	 *
	 * @param pageReqDTO 페이지 요청 데이터 전송 객체
	 * @param boardId    게시판 ID
	 * @return 페이지네이션된 여행 후기 응답 객체
	 */
	@Override
	public PageResDTO<TReviewResDTO> findTReviewListWithPaging(PageReqDTO pageReqDTO, int boardId) {
		PageDTO pageDTO = new PageDTO(pageReqDTO, boardId);

		int totalTReviewCount = treviewDao.selectTotalTReviewCount(pageDTO);

		List<TReviewResDTO> postList = treviewDao.selectTReviewListWithPaging(pageDTO);
		return new PageResDTO<>(totalTReviewCount, pageReqDTO.getPage(), postList);
	}

	/**
	 * 특정 여행 후기의 조회수를 증가시킵니다.
	 *
	 * @param trevId 여행 후기 ID
	 */
	@Override
	public void incrementTReviewCount(int trevId) {
		treviewDao.incrementTReviewCount(trevId);
	}

	/**
	 * 특정 여행 후기를 ID로 조회합니다.
	 *
	 * @param trevId 여행 후기 ID
	 * @return 여행 후기 응답 데이터 전송 객체
	 */
	@Override
	public TReviewResDTO getTReviewById(int trevId) {
		return treviewDao.getTReviewById(trevId);
	}

	/**
	 * 기존 여행 후기를 수정하고 관련 이미지를 업데이트합니다.
	 *
	 * @param treviewReqDTO  여행 후기 요청 데이터 전송 객체
	 * @param imageFiles     업로드할 새로운 이미지 파일 배열
	 * @param existingImages 기존 이미지 경로 리스트
	 */
	@Override
	public void editTReview(TReviewReqDTO treviewReqDTO, MultipartFile[] imageFiles, List<String> existingImages) {
		MemberVO member = MemberUtil.isAuthenticatedMember();

		treviewReqDTO.validate();
		TReviewResDTO treviewResDTO = treviewDao.getTReviewById(treviewReqDTO.getTrevId());
		TReviewVO tReviewVO = TReviewVO.fromDTO(treviewReqDTO, treviewResDTO);

		treviewDao.editTReview(tReviewVO);

		// 기존 이미지 삭제
		treviewDao.deleteTReviewImages(tReviewVO.getTrevId());

		// 새로운 이미지 처리
		List<MultipartFile> allFiles = new ArrayList<>();

		if (existingImages != null) {
			for (String imagePath : existingImages) {
				File file = new File(uploadDir + "/" + imagePath);

				if (file.exists()) {
					Resource resource = new FileSystemResource(file);
					MultipartFile existingFile = new DelegatingMultipartFile(resource);
					allFiles.add(existingFile);
				}
			}
		}

		if (imageFiles != null && imageFiles.length > 0) {
			allFiles.addAll(Arrays.asList(imageFiles));
		}

		TReviewImageVO tReviewImageVO = new TReviewImageVO();
		for (MultipartFile imageFile : allFiles) {
			if (!imageFile.isEmpty()) {
				String imagePath = fileUtils.saveImage(imageFile);
				treviewReqDTO.setTrevImgpath(imagePath);
				tReviewImageVO = TReviewImageVO.fromDTO(treviewReqDTO);
				treviewDao.insertTReviewImage(tReviewImageVO);
			}
		}
		saveHistory(tReviewVO.getTrevId(), tReviewVO.getMemId(), member.getMemNick(), "수정", tReviewVO.getTrevContent(),
				null);
	}

	/**
	 * 특정 여행 후기를 삭제합니다.
	 *
	 * @param trevId 삭제할 여행 후기 ID
	 */
	@Override
	public void deleteTReview(int trevId) {
		MemberVO member = MemberUtil.isAuthenticatedMember();

		TReviewResDTO treviewResDTO = treviewDao.getTReviewById(trevId);
		String trevContent = treviewResDTO != null ? treviewResDTO.getTrevContent() : null;

		treviewDao.deleteTReview(trevId);
		saveHistory(trevId, MemberUtil.isAuthenticatedMember().getMemId(), member.getMemNick(), "삭제", trevContent,
				null);

	}

	/**
	 * 특정 여행 후기를 신고합니다.
	 *
	 * @param trevId              신고할 여행 후기 ID
	 * @param reportedContentType 신고할 콘텐츠 타입
	 * @param reportReason        신고 사유
	 */
	@Override
	public void reportTReview(int trevId, MemberVO member, String reportedContentType, String reportReason) {
		ReportTReviewDTO reportTReviewDTO = new ReportTReviewDTO(member.getMemId(), trevId, reportedContentType,
				reportReason);
		ReportVO reportVO = new ReportVO(reportTReviewDTO);

		treviewDao.reportTReview(trevId);
		treviewDao.insertReportTReview(reportVO);

		saveHistory(trevId, member.getMemId(), member.getMemNick(), "신고", reportReason, ProcessStatus.RECEIVED);
	}

	/**
	 * 여행 후기글 등록, 수정, 삭제, 신고 내역 기록 메소드
	 *
	 * @param trevId  후기 ID
	 * @param memId   사용자 ID
	 * @param memNick 사용자 닉네임
	 * @param action  수행한 액션
	 * @param content 내용
	 * @param status  처리 상태
	 */
	private void saveHistory(int trevId, String memId, String memNick, String action, String content,
			ProcessStatus status) {
		HistoryVO historyVO = HistoryVO.fromTReviewHistoryVO(trevId, memId, memNick, action, content, status);

		historyService.insertTReviewHistory(historyVO);
	}

}
