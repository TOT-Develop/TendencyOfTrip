package tot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tot.domain.LodgingDTO;
import tot.domain.RestaurantDTO;
import tot.domain.TourDTO;
import tot.domain.CourseDTO;
import tot.domain.CourseResDTO;

@Repository
public class CourseDaoImpl implements CourseDao {

    private final SqlSession sqlSession;

    private static final String NAMESPACE = "tot.dao.CourseDao";
    
    @Autowired
    public CourseDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public TourDTO getTour(String toId) {
        return sqlSession.selectOne(NAMESPACE + "getTour", toId);
    }

    @Override
    public RestaurantDTO getRestaurant(int restId) {
        return sqlSession.selectOne(NAMESPACE + "getRestaurant", restId);
    }

    @Override
    public LodgingDTO getLodging(int lodId) {
        return sqlSession.selectOne(NAMESPACE + "getLodging", lodId);
    }

    @Override
    public List<CourseDTO> getCourse(int tripId) {
        return sqlSession.selectList(NAMESPACE + "getCourse", tripId);
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

		return sqlSession.selectOne(NAMESPACE + ".getCourseDetailsById", params);
	}
}
