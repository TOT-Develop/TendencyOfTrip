package tot.dao;

import java.util.List;

import tot.domain.CommentVO;
import tot.domain.ReportVO;

public interface CommentDao {

	List<CommentVO> getCommentsByReviewId(int trevId);

	CommentVO getCommentById(int commentId);

	void insertComment(CommentVO commentVO);

	void updateTopParentId(int commentId);

	void editComment(int commentId, String content);

	void deleteComment(int commentId);

	void updateCommentStatus(int commentId);

	void insertReportComment(ReportVO reportVO);

	String getUpdateDate(int commentId);

}
