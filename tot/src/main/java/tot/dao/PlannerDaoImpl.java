package tot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.CourseDTO;
import tot.domain.Hotel;
import tot.domain.Restaurant;
import tot.domain.Tour;
import tot.domain.Trip;

@Repository("PlannerDao")
public class PlannerDaoImpl implements PlannerDao {

	private final SqlSession sqlSession;

	public PlannerDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<Hotel> selectHotel(String areacode) throws Exception {
		return sqlSession.selectList("tot.dao.PlannerDao.selectHotel", areacode);
	}

	@Override
	public List<Restaurant> selectRestaurant(String areacode) throws Exception {
		return sqlSession.selectList("tot.dao.PlannerDao.selectRestaurant", areacode);
	}

	@Override
	public List<Tour> selectTour(String areacode) throws Exception {
		return sqlSession.selectList("tot.dao.PlannerDao.selectTour", areacode);
	}

	@Override
	public void insertCourse(CourseDTO courseDTO) throws Exception {
		sqlSession.insert("tot.dao.PlannerDao.insertCourse", courseDTO);
	}

	@Override
	public void insertTrip(Trip trip) throws Exception {
		sqlSession.insert("tot.dao.PlannerDao.insertTrip", trip);

	}

	@Override
	public Integer selectLatestTripId() throws Exception {
		return sqlSession.selectOne("tot.dao.PlannerDao.selectLatestTripId");
	}

	@Override
	public void updateMemberMbt(String memId, String newMbti) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("memId", memId);
		params.put("newMbti", newMbti);

		sqlSession.update("tot.dao.PlannerDao.updateMemberMbti", params);
	}

}
