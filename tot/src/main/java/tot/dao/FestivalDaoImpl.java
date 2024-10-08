package tot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.FestivalDTO;

@Repository
public class FestivalDaoImpl implements FestivalDao {

	private static final String NAMESPACE = "tot.dao.FestivalDao";
	private final SqlSession sqlSession;

	public FestivalDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<FestivalDTO> findFestivalsByDateRange(Map<String, Object> params) {
		return sqlSession.selectList(NAMESPACE + ".findFestivalsByDateRange", params);
	}

	@Override
	public List<FestivalDTO> findFestivalsByMonth(Map<String, Object> params) {
		return sqlSession.selectList(NAMESPACE + ".findFestivalsByMonth", params);
	}
}
