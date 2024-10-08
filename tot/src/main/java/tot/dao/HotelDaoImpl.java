package tot.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.Accommodation;

@Repository
public class HotelDaoImpl implements HotelDao {

	private final SqlSession sqlSession;

	public HotelDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void insertHotel(Accommodation accommodation) {
		sqlSession.insert("tot.dao.HotelDao.insertHotel", accommodation);
	}

}
