package tot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tot.common.enums.ProcessStatus;
import tot.dao.CommentDao;
import tot.domain.CommentReqDTO;
import tot.domain.CommentVO;
import tot.domain.HistoryVO;
import tot.domain.MemberVO;
import tot.domain.ReportCommentDTO;
import tot.domain.ReportVO;
import tot.util.MemberUtil;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao;
	private final HistoryService historyService;

	public CommentServiceImpl(CommentDao commentDao, HistoryService historyService) {
		this.commentDao = commentDao;
		this.historyService = historyService;
	}

	/**
	 * 특정 후기 ID에 연관된 댓글 목록을 조회합니다.
	 *
	 * @param trevId 후기 ID
	 * @return CommentVO 객체의 목록
	 */
	@Override
	public List<CommentVO> getCommentsByReviewId(int trevId) {
		List<CommentVO> comments = commentDao.getCommentsByReviewId(trevId);

		return comments;
	}

	/**
	 * 새로운 댓글을 추가합니다. 댓글의 세부 정보를 처리합니다.
	 *
	 * @param boardId       게시판 ID
	 * @param postId        게시물 ID
	 * @param commentReqDTO 댓글 요청 데이터를 담고 있는 DTO
	 */
	@Override
	public void insertComment(String boardId, int postId, CommentReqDTO commentReqDTO) {
		MemberVO member = MemberUtil.isAuthenticatedMember();

		CommentVO commentVO = new CommentVO(postId, commentReqDTO);
		handleSetCommentInfo(commentVO, postId);

		commentDao.insertComment(commentVO);

		int insertedCommentId = commentVO.getCommentId();
		if (commentVO.getParentId() == 0) {
			commentVO.setCommentInfo(insertedCommentId, 0);

			updateTopParentId(commentVO);
		}

		saveHistory(insertedCommentId, member.getMemId(), member.getMemNick(), "등록", commentReqDTO.getContent(), null);

	}

	/**
	 * 기존 댓글의 내용을 업데이트합니다.
	 *
	 * @param commentId 업데이트할 댓글 ID
	 * @param content   새로운 댓글 내용
	 */
	@Override
	public void editComment(int commentId, String content) {
		MemberVO member = MemberUtil.isAuthenticatedMember();

		commentDao.editComment(commentId, content);
		saveHistory(commentId, member.getMemId(), member.getMemNick(), "수정", content, null);
	}

	/**
	 * 댓글을 삭제합니다. 해당 댓글의 상태를 삭제로 업데이트합니다.
	 *
	 * @param commentId 삭제할 댓글 ID
	 */
	@Override
	public void deleteComment(int commentId) {
		CommentVO existingComment = commentDao.getCommentById(commentId);
		if (existingComment != null) {
			MemberVO member = MemberUtil.isAuthenticatedMember();

			commentDao.deleteComment(commentId);
			saveHistory(commentId, member.getMemId(), member.getMemNick(), "삭제", existingComment.getContent(), null);
		}

	}

	/**
	 * 댓글을 신고합니다. 해당 댓글의 신고 상태를 업데이트합니다.
	 *
	 * @param commentId           신고할 댓글 ID
	 * @param reportedContentType 신고된 콘텐츠 유형
	 * @param reportReason        신고 사유
	 */
	@Override
	public void reportComment(int commentId, String reportedContentType, String reportReason) {
		MemberVO member = MemberUtil.isAuthenticatedMember();

		ReportCommentDTO reportCommentDTO = new ReportCommentDTO(member.getMemId(), commentId, reportedContentType,
				reportReason);
		ReportVO reportVO = new ReportVO(reportCommentDTO);

		commentDao.updateCommentStatus(commentId);
		commentDao.insertReportComment(reportVO);

		saveHistory(commentId, member.getMemId(), member.getMemNick(), "신고", reportReason, ProcessStatus.RECEIVED);
	}

	/**
	 * 댓글 수정 또는 삭제에 대한 업데이트 시간을 반환합니다.
	 *
	 * @param commentId 댓글 ID
	 * @return 업데이트된 날짜 및 시간
	 */
	@Override
	public String getUpdateDate(int commentId) {
		return commentDao.getUpdateDate(commentId);
	}

	/**
	 * 추가되는 댓글의 최상위 부모 댓글 ID 및 깊이를 설정합니다. 부모 댓글 ID가 있을 경우 대댓글로 간주합니다.
	 *
	 * @param vo     댓글 정보 객체
	 * @param postId 게시물 ID
	 */
	private void handleSetCommentInfo(CommentVO vo, int postId) {
		if (vo.getParentId() != 0) {
			CommentVO parentComment = commentDao.getCommentById(vo.getParentId());

			if (parentComment != null) {
				vo.setCommentInfo(parentComment.getTopParentId(), parentComment.getDepth() + 1);
			}
		} else {
			vo.setCommentInfo(vo.getCommentId(), 0);
		}
	}

	/**
	 * 최상위 댓글의 topParentId를 업데이트합니다.
	 *
	 * @param commentVO 댓글 정보 객체
	 */
	private void updateTopParentId(CommentVO commentVO) {
		commentDao.updateTopParentId(commentVO.getCommentId());
	}

	/**
	 * 여행 후기글 등록, 수정, 삭제, 신고 내역을 기록합니다.
	 *
	 * @param trevcId 댓글 ID
	 * @param memId   회원 ID
	 * @param memNick 회원 닉네임
	 * @param action  수행한 작업
	 * @param content 댓글 내용
	 * @param status  처리 상태
	 */
	private void saveHistory(int trevcId, String memId, String memNick, String action, String content,
			ProcessStatus status) {
		HistoryVO historyVO = HistoryVO.fromCommentHistoryVO(trevcId, memId, memNick, action, content, status);

		historyService.insertCommentHistory(historyVO);
	}

}
