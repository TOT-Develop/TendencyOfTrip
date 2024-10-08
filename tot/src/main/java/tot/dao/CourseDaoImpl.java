package tot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import tot.domain.CourseDTO;
import tot.domain.CourseResDTO;
import tot.domain.LodgingDTO;
import tot.domain.RestaurantDTO;
import tot.domain.TourDTO;

@Repository
public class CourseDaoImpl implements CourseDao {

	private final SqlSession sqlSession;

	private static final String NAMESPACE = "tot.dao.CourseDao";

	public CourseDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public TourDTO getTour(String toId) {
		return sqlSession.selectOne(NAMESPACE + ".getTour", toId);
	}

	@Override
	public RestaurantDTO getRestaurant(int restId) {
		return sqlSession.selectOne(NAMESPACE + ".getRestaurant", restId);
	}

	@Override
	public LodgingDTO getLodging(int lodId) {
		return sqlSession.selectOne(NAMESPACE + ".getLodging", lodId);
	}

	@Override
	public List<CourseDTO> getCourse(int tripId) {
		return sqlSession.selectList(NAMESPACE + ".getCourse", tripId);
	}

	@Override
	public CourseDTO getCourseById(String courseId) {
		return sqlSession.selectOne(NAMESPACE + ".getCourseById", courseId);
	}

	@Override
	public List<CourseDTO> getCourseByTripId(int tripId) {
		return sqlSession.selectList(NAMESPACE + ".getCourseByTripId", tripId);
	}

	@Override
	public CourseResDTO getCourseDetailsById(String dcourseType, int dcourseId) {
		Map<String, Object> params = new HashMap<>();
		params.put("dcourseType", dcourseType);
		params.put("dcourseId", dcourseId);
		System.out.println("Params: " + params); // 로그 출력
		return sqlSession.selectOne(NAMESPACE + ".getCourseDetailsById", params);
	}

	@Override
	public List<LodgingDTO> selectHotel(String areacode) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectHotel", areacode);
	}

	@Override
	public List<RestaurantDTO> selectRestaurant(String areacode) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectRestaurant", areacode);
	}

	@Override
	public List<TourDTO> selectTour(String areacode) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectTour", areacode);
	}

	@Override
	public void updateDcourse(Integer courId, String dcourse, Long tripId) {
		System.out.println(
				"DAO updateDcourse with params: " + Map.of("courId", courId, "dcourse", dcourse, "tripId", tripId));
		sqlSession.update(NAMESPACE + ".updateDcourse", Map.of("courId", courId, "dcourse", dcourse, "tripId", tripId));
	}

}
