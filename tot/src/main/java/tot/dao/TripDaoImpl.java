package tot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.TripDTO;

@Repository
public class TripDaoImpl implements TripDao {

	private static final String NAMESPACE = "tot.dao.TripDao";
	private final SqlSession sqlSession;

	public TripDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<TripDTO> getTripsByMemIdWithPaging(Map<String, Object> params) {
		return sqlSession.selectList(NAMESPACE + ".getTripsByMemIdWithPaging", params);
	}

	@Override
	public List<TripDTO> getTripsByMemId(String memId) {
		return sqlSession.selectList(NAMESPACE + ".getTripsByMemId", memId);
	}
	
	@Override
	public TripDTO getTripById(int tripId) {
		return sqlSession.selectOne(NAMESPACE + ".getTripById", tripId);
	}

	@Override
	public int getTotalTripsByMemId(String memId) {
		return sqlSession.selectOne(NAMESPACE + ".getTotalTripsByMemId", memId);
	}

}
