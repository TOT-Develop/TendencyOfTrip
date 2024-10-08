package tot.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.MemBanHistoryDTO;

@Repository
public class MemBanHistoryDaoImpl implements MemBanHistoryDao {

	private static final String NAMESPACE = "tot.dao.MemBanHistoryDao";
	private final SqlSession sqlSession;

	public MemBanHistoryDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void insertBanHistory(MemBanHistoryDTO banHistory) {
		sqlSession.insert(NAMESPACE + ".insertBanHistory", banHistory);
	}

	@Override
	public List<MemBanHistoryDTO> getBanHistoryByMemId(String memId) {
		return sqlSession.selectList(NAMESPACE + ".getBanHistoryByMemId", memId);
	}

	@Override
	public void updateLiftHistory(MemBanHistoryDTO history) {
		// 현재 시간으로 설정
		history.setBanLifted(new Timestamp(System.currentTimeMillis()));
		sqlSession.update(NAMESPACE + ".updateLiftHistory", history);
	}
}
