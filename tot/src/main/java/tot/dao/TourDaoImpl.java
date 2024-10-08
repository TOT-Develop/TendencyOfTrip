package tot.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.TourVO;

@Repository
public class TourDaoImpl implements TourDao {

	private static final String NAMESPACE = "tot.dao.TourDao";
	private final SqlSession sqlSession;

	public TourDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public TourVO getTourById(String tourId) {
		return sqlSession.selectOne(NAMESPACE + ".getTourById", tourId);
	}

}
