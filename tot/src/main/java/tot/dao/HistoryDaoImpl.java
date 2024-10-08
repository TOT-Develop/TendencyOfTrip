package tot.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.HistoryVO;

@Repository
public class HistoryDaoImpl implements HistoryDao {

	private static final String NAMESPACE = "tot.dao.HistoryDao";
	private final SqlSession sqlSession;

	public HistoryDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void insertTReviewHistory(HistoryVO historyVO) {
		sqlSession.insert(NAMESPACE + ".insertTReviewHistory", historyVO);
	}

	@Override
	public void insertCommentHistory(HistoryVO historyVO) {
		sqlSession.insert(NAMESPACE + ".insertCommentHistory", historyVO);
	}

	@Override
	public List<HistoryVO> getTReviewHistorysById(int trevId) {
		return sqlSession.selectList(NAMESPACE + ".getTReviewHistorysById", trevId);
	}

}
