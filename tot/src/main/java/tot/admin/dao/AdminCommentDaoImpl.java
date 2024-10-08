package tot.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.common.page.PageDTO;
import tot.domain.CommentVO;

@Repository
public class AdminCommentDaoImpl implements AdminCommentDao {

	private static final String NAMESPACE = "tot.admin.dao.AdminCommentDao";
	private final SqlSession sqlSession;

	public AdminCommentDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<CommentVO> getCommentsByReviewId(int trevId) {
		return sqlSession.selectList(NAMESPACE + ".getCommentsByReviewId", trevId);
	}

	@Override
	public List<String> findInactiveParentComments(List<Integer> trevcIds) {
		return sqlSession.selectList(NAMESPACE + ".findInactiveParentComments", trevcIds);
	}

	@Override
	public void updateCommentStatus(String status, List<Integer> trevcIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("status", status);
		params.put("trevcIds", trevcIds);

		sqlSession.update(NAMESPACE + ".updateCommentStatus", params);
	}

	@Override
	public int selectTotalCommentCount(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".selectTotalCommentCount", pageDTO);
	}

	@Override
	public int selectTotalCommentCountById(PageDTO pageDTO) {
		return sqlSession.selectOne(NAMESPACE + ".selectTotalCommentCountById", pageDTO);
	}

	@Override
	public List<CommentVO> selectCommentListWithPaging(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".selectCommentListWithPaging", pageDTO);
	}

	@Override
	public List<CommentVO> selectCommentListWithPagingById(PageDTO pageDTO) {
		return sqlSession.selectList(NAMESPACE + ".selectCommentListWithPagingById", pageDTO);
	}

}
