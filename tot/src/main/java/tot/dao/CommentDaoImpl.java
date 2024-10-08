package tot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.CommentVO;
import tot.domain.ReportVO;

@Repository
public class CommentDaoImpl implements CommentDao {

	private static final String NAMESPACE = "tot.dao.CommentDao";
	private final SqlSession sqlSession;

	public CommentDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<CommentVO> getCommentsByReviewId(int trevId) {
		return sqlSession.selectList(NAMESPACE + ".getCommentsByReviewId", trevId);
	}

	@Override
	public CommentVO getCommentById(int commentId) {
		return sqlSession.selectOne(NAMESPACE + ".getCommentById", commentId);
	}

	@Override
	public void insertComment(CommentVO commentVO) {
		sqlSession.insert(NAMESPACE + ".insertComment", commentVO);
	}

	@Override
	public void updateTopParentId(int commentId) {
		sqlSession.update(NAMESPACE + ".updateTopParentId", commentId);
	}

	@Override
	public void editComment(int commentId, String content) {
		Map<String, Object> params = new HashMap<>();
		params.put("commentId", commentId);
		params.put("content", content);

		sqlSession.update(NAMESPACE + ".editComment", params);
	}

	@Override
	public void deleteComment(int commentId) {
		sqlSession.update(NAMESPACE + ".deleteComment", commentId);
	}

	@Override
	public void updateCommentStatus(int commentId) {
		sqlSession.update(NAMESPACE + ".updateCommentStatus", commentId);
	}

	@Override
	public void insertReportComment(ReportVO reportVO) {
		sqlSession.update(NAMESPACE + ".insertReportComment", reportVO);
	}

	@Override
	public String getUpdateDate(int commentId) {
		return sqlSession.selectOne(NAMESPACE + ".getUpdateDate", commentId);
	}

}
