package tot.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.admin.dao.AdminCommentDao;
import tot.common.page.PageDTO;
import tot.common.page.PageReqDTO;
import tot.common.page.PageResDTO;
import tot.domain.CommentVO;

@Service
public class AdminCommentServiceImpl implements AdminCommentService {

	private final AdminCommentDao adminCommentDao;

	public AdminCommentServiceImpl(AdminCommentDao adminCommentDao) {
		this.adminCommentDao = adminCommentDao;
	}

	/**
	 * 특정 여행 후기 ID에 대한 댓글 목록을 조회합니다.
	 *
	 * @param trevId 여행 후기 ID
	 * @return 해당 후기의 댓글 목록
	 */
	@Override
	public List<CommentVO> getCommentsByReviewId(int trevId) {
		List<CommentVO> comments = adminCommentDao.getCommentsByReviewId(trevId);

		return comments;
	}

	/**
	 * 댓글의 상태를 업데이트합니다.
	 *
	 * @param status   상태 (활성화/비활성화)
	 * @param trevcIds 업데이트할 댓글 ID 목록
	 */
	@Override
	public void updateCommentStatus(String status, List<Integer> trevcIds) {
		// 상위 댓글이 비활성화 상태인지 확인
		List<String> inactiveParentComments = adminCommentDao.findInactiveParentComments(trevcIds);

		// 상위 댓글이 비활성화된 경우 예외 처리
		if (!inactiveParentComments.isEmpty() && "active".equals(status)) {
			throw new IllegalStateException("상위 댓글이 비활성화되어 있어 하위 댓글을 활성화할 수 없습니다.");
		}

		// 검증 통과 시 상태 업데이트
		adminCommentDao.updateCommentStatus(status, trevcIds);
	}

	/**
	 * 페이지네이션된 댓글 목록을 조회합니다.
	 *
	 * @param pageReqDTO 페이지 요청 데이터 전송 객체
	 * @param boardId    게시판 ID
	 * @return 페이지네이션된 댓글 응답 객체
	 */
	@Override
	public PageResDTO<CommentVO> findCommentListWithPaging(PageReqDTO pageReqDTO, int boardId) {
		PageDTO pageDTO = new PageDTO(pageReqDTO, boardId);
		int totalCommentCount = adminCommentDao.selectTotalCommentCount(pageDTO);

		List<CommentVO> commentList = adminCommentDao.selectCommentListWithPaging(pageDTO);

		return new PageResDTO<>(totalCommentCount, pageReqDTO.getPage(), commentList);
	}

	/**
	 * 페이지네이션된 댓글 목록을 조회합니다.
	 *
	 * @param pageReqDTO 페이지 요청 데이터 전송 객체
	 * @param boardId    게시판 ID
	 * @param postId     게시물 ID
	 * @return 페이지네이션된 댓글 응답 객체
	 */
	@Override
	public PageResDTO<CommentVO> findCommentListWithPaging(PageReqDTO pageReqDTO, int boardId, int postId) {
		PageDTO pageDTO = new PageDTO(pageReqDTO, boardId, postId);
		int totalCommentCount = adminCommentDao.selectTotalCommentCountById(pageDTO);
		
		List<CommentVO> commentList = adminCommentDao.selectCommentListWithPagingById(pageDTO);
		
		return new PageResDTO<>(totalCommentCount, pageReqDTO.getPage(), commentList);
	}

}
